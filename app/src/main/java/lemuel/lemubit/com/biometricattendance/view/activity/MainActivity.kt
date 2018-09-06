package lemuel.lemubit.com.biometricattendance.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.mapzen.speakerbox.Speakerbox
import com.rollbar.android.Rollbar
import com.wepoy.fp.FingerprintImage
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.model.DBHelper
import lemuel.lemubit.com.biometricattendance.nativeFeatures.NativeSensor
import lemuel.lemubit.com.biometricattendance.util.AdminDialogHelper
import lemuel.lemubit.com.biometricattendance.util.DBOperation
import lemuel.lemubit.com.biometricattendance.util.ProgressDialogHelper
import lemuel.lemubit.com.biometricattendance.util.TimeHelper
import lemuel.lemubit.com.biometricattendance.view.AdminLogin
import lemuel.lemubit.com.biometricattendance.view.IFingerPrintOperation
import lemuel.lemubit.com.biometricattendance.view.IUIOperations

class MainActivity : AppCompatActivity(), IUIOperations, AdminLogin, IFingerPrintOperation {

    private lateinit var progressMaterialDialog: MaterialDialog
    private lateinit var adminMaterialDialog: MaterialDialog
    private var clockOperation: Int = -1
    lateinit var speakerbox: Speakerbox

    //Variables to check which operation was selected by user,
    //this is different from ClockedState which describes if the user has been clocked in or out
    private val clockInOperation = 1
    private val clockOutOperation = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setProgressDialog()
        Realm.init(this)
        Rollbar.init(this)
        NativeSensor.init(application.applicationContext, this)
        setAdminLoginDialog()

        speakerbox = Speakerbox(application)
        speakerbox.enableVolumeControl(this)

        btn_clockIn.setOnClickListener {
            NativeSensor.verifyAndCaptureUser(this, this, this)?.subscribe(observer)
            clockInOperation()
        }

        btn_clockOut.setOnClickListener {
            NativeSensor.verifyAndCaptureUser(this, this, this)?.subscribe(observer)
            clockOutOperation()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_admin -> {
                adminMaterialDialog.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showProgressDialog(title: String, message: String) {
        runOnUiThread { progressMaterialDialog.show() }
    }

    override fun dismissProgressDialog() {
        runOnUiThread { progressMaterialDialog.dismiss() }
    }

    override fun setProgressDialog() {
        progressMaterialDialog = ProgressDialogHelper.getBuilder(this,
                resources.getString(R.string.enrol),
                resources.getString(R.string.press_finger))
                .build()
    }

    override fun showInfoToast(info: String) {
        runOnUiThread { Toast.makeText(this, "Message: $info", Toast.LENGTH_SHORT).show() }
    }

    override fun adminLoginSuccess(password: String) {
        val bundle = Bundle()
        bundle.putString("password", password)
        startActivity(Intent(this, AdminHomeActivity::class.java).putExtras(bundle))
    }

    override fun adminLoginFailed() {
        Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
    }

    override fun updateFingerPrintImage(fi: FingerprintImage?) {
    }

    private fun updateLastOperation(name: String) {
        var clockOperation = "clocked"
        when (getOperation()) {
            clockInOperation -> clockOperation = " clocked in "
            clockOutOperation -> clockOperation = " clocked out "
        }

        val time = TimeHelper.currentTime

        val message = "$name$clockOperation by $time"

        runOnUiThread { txt_last_operation.text = message }
    }

    private fun welcomeUser(username: String) {
        runOnUiThread {
            if (getOperation() == clockInOperation) {
                speakerbox.play("Welcome $username")
            } else {
                speakerbox.play("Goodbye $username")
            }
        }

    }

    private fun clockInOperation() {
        clockOperation = clockInOperation
    }

    private fun clockOutOperation() {
        clockOperation = clockOutOperation
    }

    private fun getOperation(): Int {
        return clockOperation
    }

    private fun setAdminLoginDialog() {
        adminMaterialDialog = AdminDialogHelper.getBuilder(this, this).build()
    }

    private val observer = object : Observer<Int?> {
        override fun onNext(id: Int) {
            if (id >= 0) {
                val dbOperation = DBHelper.clockUser(id, getOperation())
                if (dbOperation == DBOperation.CLOCK_OPERATION_ALREADY_PERFORMED) {
                    showInfoToast("You cannot perform that operation")
                }

                if (dbOperation == DBOperation.SUCCESSFUL) {
                    showInfoToast("SUCCESSFUL")
                    var username = DBHelper.getUserName(id)
                    updateLastOperation(username)
                    welcomeUser(username)
                }

                if (dbOperation == DBOperation.FAILED) {
                    showInfoToast("FAILED")
                }
            }
        }

        override fun onComplete() {
            Log.d("RxJAVA:", "completed")
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onError(e: Throwable) {
            Log.d("RxJAVA:", e.message)
        }

    }

}
