package lemuel.lemubit.com.biometricattendance.nativeFeatures

import android.annotation.SuppressLint
import android.content.Context
import com.wepoy.fp.Bione
import com.wepoy.fp.FingerprintImage
import com.wepoy.fp.FingerprintScanner
import com.wepoy.util.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.view.IFingerPrintOperation
import lemuel.lemubit.com.biometricattendance.view.IUIOperations

object NativeSensor {
    @SuppressLint("SdCardPath")
    private val FP_DB_PATH = "/sdcard/fp.db"

    @JvmStatic
/*Initialize fingerprint device*/
    fun init(context: Context, iuiOperations: IUIOperations) = object : Thread() {
        var mScanner: FingerprintScanner = FingerprintScanner.getInstance(context)
        override fun run() {
            iuiOperations.showInfoToast(context.getString(R.string.preparing_device))

            if ((mScanner.powerOn()) != FingerprintScanner.RESULT_OK) iuiOperations.showInfoToast(context.getString(R.string.fingerprint_device_power_on_failed))
            else iuiOperations.showInfoToast("Device power on success")

            if ((mScanner.open()) != FingerprintScanner.RESULT_OK) iuiOperations.showInfoToast(context.getString(R.string.fingerprint_device_open_failed) + mScanner.open())
            else iuiOperations.showInfoToast(context.getString(R.string.fingerprint_device_open_success))

            if ((Bione.initialize(context, FP_DB_PATH)) != Bione.RESULT_OK) iuiOperations.showInfoToast(context.getString(R.string.algorithm_initialization_failed) + Bione.initialize(context, FP_DB_PATH))
            else iuiOperations.showInfoToast("Device algorithm initialization success")
        }
    }.start()

    @JvmStatic
    /**@Returns the raw data of fingerprint scan**/
    private fun getFingerPrintData(currentContext: Context, iuiOperations: IUIOperations, iFingerPrintOperation: IFingerPrintOperation): Result? {
        val fingerprintScanner: FingerprintScanner = FingerprintScanner.getInstance(currentContext.applicationContext)
        var fingerprintImage: FingerprintImage
        var result: Result?

        iuiOperations.showProgressDialog(currentContext.getString(R.string.loading), currentContext.getString(R.string.press_finger))
        fingerprintScanner.prepare()

        do {
            result = fingerprintScanner.capture()
        } while (result!!.error == FingerprintScanner.NO_FINGER)

        fingerprintScanner.finish()
        if (result.error != FingerprintScanner.RESULT_OK) {
            iuiOperations.showInfoToast(currentContext.getString(R.string.capture_image_failed))
        }

        fingerprintImage = result.data as FingerprintImage
        iuiOperations.showProgressDialog(currentContext.getString(R.string.loading), currentContext.getString(R.string.enrolling))
        result = Bione.extractFeature(fingerprintImage)
        if (result.error != Bione.RESULT_OK) {
            iuiOperations.showInfoToast(currentContext.getString(R.string.enroll_failed_because_of_extract_feature))
            result = null
        }

        iFingerPrintOperation.updateFingerPrintImage(fingerprintImage)
        iuiOperations.dismissProgressDialog()

        return result
    }

    @JvmStatic
    /**@Returns the ID of saved fingerprint data**/
    private fun saveFingerPrint(context: Context, result: Result, iuiOperations: IUIOperations): Int {
        var fingerPrintData: ByteArray?
        var fingerPrintTemplate: ByteArray?
        var res: Result? = result
        var id = 0

        if (res == null) {
            iuiOperations.dismissProgressDialog()
            throw RuntimeException("result is null")
        }
        fingerPrintData = res.data as ByteArray
        res = Bione.makeTemplate(fingerPrintData, fingerPrintData, fingerPrintData)

        if (res.error != Bione.RESULT_OK) {
            iuiOperations.dismissProgressDialog()
            iuiOperations.showInfoToast(context.getString(R.string.enroll_failed_because_of_make_template))
            throw RuntimeException("result is not OK")
        }

        fingerPrintTemplate = res.data as ByteArray
        id = Bione.getFreeID()

        if (id < 0) {
            iuiOperations.dismissProgressDialog()
            iuiOperations.showInfoToast(context.getString(R.string.enroll_failed_because_of_get_id))
            throw RuntimeException("result is not OK")
        }

        val ret = Bione.enroll(id, fingerPrintTemplate)

        if (ret != Bione.RESULT_OK) {
            iuiOperations.dismissProgressDialog()
            iuiOperations.showInfoToast(context.getString(R.string.enroll_failed_because_of_error))
            throw RuntimeException("result is not OK")
        }

        iuiOperations.showInfoToast(context.getString(R.string.enroll_success) + id)

        iuiOperations.dismissProgressDialog()
        return id
    }

   private fun getUserID(result: Result): Int? {
        var fpFeat: ByteArray?
        var res: Result? = result
        var userID: Int? = null

        do {
            if (res == null) break
            fpFeat = res.data as ByteArray
            userID = Bione.identify(fpFeat);
        } while (false)

        return userID
    }

    @JvmStatic
            /**@Returns an Observable which would run getFingerPrintData() in a background thread**/
    fun getAndSaveFingerprint(currentContext: Context, iuiOperations: IUIOperations, iFingerPrintOperation: IFingerPrintOperation): Observable<Int> {
        return Observable.defer {
            Observable.just(getFingerPrintData(currentContext, iuiOperations, iFingerPrintOperation))
                    .map { result -> saveFingerPrint(currentContext.applicationContext, result, iuiOperations) }
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    fun verifyAndCaptureUser(currentContext: Context, iuiOperations: IUIOperations, iFingerPrintOperation: IFingerPrintOperation): Observable<Int?>? {
        return Observable.defer {
            Observable.just(getFingerPrintData(currentContext, iuiOperations,iFingerPrintOperation))
                    .map { result -> getUserID(result) }
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}