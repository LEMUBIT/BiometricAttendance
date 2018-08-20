package lemuel.lemubit.com.biometricattendance.view.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.view.IUIOperations
import lemuel.lemubit.com.biometricattendance.view.fragment.*

class NewCaptureActivity : AppCompatActivity(),
        IUIOperations,
        RegistrationInfoFragment.RegistrationInfoListener,
        RegistrationTakePicFragment.RegistrationTakePicListener,
        RegistrationLeftHandFragment.RegistrationLeftHandListener,
        RegistrationRightHandFragment.RegistrationRightHandListener,
        RegistrationFinalFragment.RegistrationFinalListener {

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
    lateinit var args: Bundle
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
        addNewFragment(RegistrationTakePicFragment(), null)
    }

    override fun onUserPicGotten(capturedPhotoByteArray: ByteArray) {
        this.capturedPhotoByteArray = capturedPhotoByteArray
        addNewFragment(RegistrationLeftHandFragment(), null)
    }

    override fun onLeftHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {
        leftFingerIDMap = fingerPrintIdMap
        addNewFragment(RegistrationRightHandFragment(), null)
    }

    override fun onRightHandRegistered(fingerPrintIdMap: HashMap<Int, Int>) {
        rightFingerIDMap = fingerPrintIdMap
        setBundle()
        addNewFragment(RegistrationFinalFragment(), args)
    }

    override fun onUserInfoConfirmed() {
        TODO("Use here to save all users information and continue, show dialog to make user confirm")
    }

    /*Set the bundle to be sent to Final Fragment*/
    private fun setBundle() {
        args = Bundle()
        args.putString("firstName", firstName)
        args.putString("lastName", lastName)
        args.putString("sex", sex)
        args.putString("dateOfBirth", dateOfBirth)
        args.putString("email", email)
        args.putString("phoneNumber", phoneNumber)
        args.putByteArray("capturedPhotoByteArray", capturedPhotoByteArray)
    }

    private fun addFirstFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.reg_frag_placeholder, fragment)
        ft.commit()
    }

    private fun addNewFragment(fragment: Fragment, bundle: Bundle?) {
        if (bundle != null) {
            fragment.arguments = bundle
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        ft.replace(R.id.reg_frag_placeholder, fragment)
        ft.addToBackStack(fragment.tag)
        ft.commit()
    }


}
