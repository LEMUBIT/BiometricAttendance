package lemuel.lemubit.com.biometricattendance.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.view.IUIOperations
import lemuel.lemubit.com.biometricattendance.view.fragment.RegistrationInfoFragment
import lemuel.lemubit.com.biometricattendance.view.fragment.RegistrationLeftHandFragment
import lemuel.lemubit.com.biometricattendance.view.fragment.RegistrationRightHandFragment
import lemuel.lemubit.com.biometricattendance.view.fragment.RegistrationTakePicFragment

class NewCaptureActivity : AppCompatActivity(), IUIOperations, RegistrationInfoFragment.RegistrationInfoListener, RegistrationTakePicFragment.RegistrationTakePicListener, RegistrationLeftHandFragment.RegistrationLeftHandListener, RegistrationRightHandFragment.RegistrationRightHandListener {

    lateinit var dialogBuilder: MaterialDialog.Builder
    lateinit var dialog: MaterialDialog
    var leftFingerIDMap = HashMap<Int, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_capture)
        setProgressDialog()
    }

    override fun showProgressDialog(title: String, message: String) {
        this.runOnUiThread { dialog.show() }
    }

    override fun dismissProgressDialog() {
        this.runOnUiThread { dialog.dismiss() }
    }

    override fun setProgressDialog() {
        dialogBuilder = MaterialDialog.Builder(this)
                .title(R.string.enrol)
                .content(R.string.press_finger)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
        dialog = dialogBuilder.build()
    }

    override fun showInfoToast(info: String) {

    }

    override fun onUserInfoGotten() {

    }

    override fun onUserPicGotten() {

    }

    override fun onLeftHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {
        leftFingerIDMap = fingerPrintIdMap
        //todo transit to next fragment
    }

    override fun onRightHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {

    }
}
