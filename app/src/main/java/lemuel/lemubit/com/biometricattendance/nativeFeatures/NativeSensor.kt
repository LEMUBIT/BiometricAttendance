package lemuel.lemubit.com.biometricattendance.nativeFeatures

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import com.wepoy.fp.Bione
import com.wepoy.fp.FingerprintImage
import com.wepoy.fp.FingerprintScanner
import com.wepoy.util.Result
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.view.IFingerPrintOperation
import lemuel.lemubit.com.biometricattendance.view.IUIOperations

object NativeSensor {
    @SuppressLint("SdCardPath")
    val FP_DB_PATH = "/sdcard/fp.db"

    /*Initialize fingerprint device*/
    fun init(activity: Activity, iuiOperations: IUIOperations) = object : Thread() {
        var mScanner: FingerprintScanner = FingerprintScanner.getInstance(activity.applicationContext)
        override fun run() {
            iuiOperations.showInfoToast(activity.getString(R.string.preparing_device))

            if ((mScanner.powerOn()) != FingerprintScanner.RESULT_OK) iuiOperations.showInfoToast(activity.getString(R.string.fingerprint_device_power_on_failed))

            if ((mScanner.open()) != FingerprintScanner.RESULT_OK) iuiOperations.showInfoToast(activity.getString(R.string.fingerprint_device_open_failed))
            else iuiOperations.showInfoToast(activity.getString(R.string.fingerprint_device_open_success))

            if ((Bione.initialize(activity, FP_DB_PATH)) != Bione.RESULT_OK) iuiOperations.showInfoToast(activity.getString(R.string.algorithm_initialization_failed))
        }
    }.start()


    /**@Returns the raw data of fingerprint scan**/
    fun getFingerPrintData(application: Application, iFingerPrintOperation: IFingerPrintOperation): Result? {
        val fingerprintScanner: FingerprintScanner = FingerprintScanner.getInstance(application.applicationContext)
        var fingerprintImage: FingerprintImage
        var result: Result?

        iFingerPrintOperation.showProgressDialog(application.getString(R.string.loading), application.getString(R.string.press_finger))
        fingerprintScanner.prepare()

        do {
            result = fingerprintScanner.capture()
        } while (result!!.error == FingerprintScanner.NO_FINGER)

        fingerprintScanner.finish()
        if (result.error != FingerprintScanner.RESULT_OK) {
            iFingerPrintOperation.showInfoToast(application.getString(R.string.capture_image_failed))
        }

        fingerprintImage = result.data as FingerprintImage
        iFingerPrintOperation.showProgressDialog(application.getString(R.string.loading), application.getString(R.string.enrolling))
        result = Bione.extractFeature(fingerprintImage)
        if (result.error != Bione.RESULT_OK) {
            iFingerPrintOperation.showInfoToast(application.getString(R.string.enroll_failed_because_of_extract_feature))
            result = null
        }

        iFingerPrintOperation.updateFingerPrintImage(fingerprintImage)
        iFingerPrintOperation.dismissProgressDialog()

        return result
    }

    /**@Returns the ID of saved fingerprint data**/
    fun saveFingerPrint(application: Application, result: Result, iFingerPrintOperation: IFingerPrintOperation): Int {
        var fingerPrintData: ByteArray?
        var fingerPrintTemplate: ByteArray?
        var res: Result? = result
        var id = 0

        if (res == null) {
            iFingerPrintOperation.dismissProgressDialog()
            throw RuntimeException("result is null")
        }
        fingerPrintData = res.data as ByteArray
        res = Bione.makeTemplate(fingerPrintData, fingerPrintData, fingerPrintData)

        if (res.error != Bione.RESULT_OK) {
            iFingerPrintOperation.dismissProgressDialog()
            iFingerPrintOperation.showInfoToast(application.getString(R.string.enroll_failed_because_of_make_template))
            throw RuntimeException("result is not OK")
        }

        fingerPrintTemplate = res.data as ByteArray
        id = Bione.getFreeID()

        if (id < 0) {
            iFingerPrintOperation.dismissProgressDialog()
            iFingerPrintOperation.showInfoToast(application.getString(R.string.enroll_failed_because_of_get_id))
            throw RuntimeException("result is not OK")
        }

        val ret = Bione.enroll(id, fingerPrintTemplate)

        if (ret != Bione.RESULT_OK) {
            iFingerPrintOperation.dismissProgressDialog()
            iFingerPrintOperation.showInfoToast(application.getString(R.string.enroll_failed_because_of_error))
            throw RuntimeException("result is not OK")
        }

        iFingerPrintOperation.showInfoToast(application.getString(R.string.enroll_success) + id)

        iFingerPrintOperation.dismissProgressDialog()
        return id
    }
}