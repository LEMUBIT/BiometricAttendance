package lemuel.lemubit.com.biometricattendance.view.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
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
    var rightFingerIDMap = HashMap<Int, Int>()

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var sex: String
    lateinit var dateOfBirth: String
    lateinit var email: String
    lateinit var phoneNumber: String

    lateinit var capturedPhotoByteArray: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_capture)
        setProgressDialog()
        addFirstFragment(RegistrationInfoFragment())

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
        Activity.RESULT_OK
    }

    override fun onUserInfoGotten(firstName: String, lastName: String, sex: String, dateOfBirth: String, email: String, phoneNumber: String) {
        this.firstName = firstName
        this.lastName = lastName
        this.sex = sex
        this.dateOfBirth = dateOfBirth
        this.email = email
        this.phoneNumber = phoneNumber
        addNewFragment(RegistrationTakePicFragment())
    }

    override fun onUserPicGotten(capturedPhotoByteArray: ByteArray) {
        this.capturedPhotoByteArray = capturedPhotoByteArray
        addNewFragment(RegistrationLeftHandFragment())
    }

    override fun onLeftHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {
        leftFingerIDMap = fingerPrintIdMap
        //todo transit to next fragment
    }

    override fun onRightHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {
        rightFingerIDMap = fingerPrintIdMap
        //todo transit to next fragment
    }



    private fun addNewFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        ft.replace(R.id.reg_frag_placeholder, fragment)
        ft.addToBackStack(fragment.tag)
        ft.commit()
    }

    private fun addFirstFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.reg_frag_placeholder, fragment)
        ft.commit()
    }
}
