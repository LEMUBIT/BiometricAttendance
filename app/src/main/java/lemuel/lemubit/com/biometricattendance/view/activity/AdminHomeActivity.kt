package lemuel.lemubit.com.biometricattendance.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_admin_home.*
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.model.DBHelper
import lemuel.lemubit.com.biometricattendance.nativeFeatures.NativeSensor
import lemuel.lemubit.com.biometricattendance.util.AppFirstRunChecker

class AdminHomeActivity : AppCompatActivity() {
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        password = intent.getStringExtra("password")
        btn_newcapture.setOnClickListener {
            startActivity(Intent(this, NewCaptureActivity::class.java))
        }

        btn_viewUsers.setOnClickListener {
            startActivity(Intent(this, ViewUsersActivity::class.java))
        }

        btn_viewAttendance.setOnClickListener {
            startActivity(Intent(this, ViewAttendanceActivity::class.java))
        }

        btn_clearDb.setOnClickListener {
            val message = NativeSensor.clearDatabase()
            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()

            //AppFirstRunChecker.refreshApp(this@AdminHomeActivity)
            Toast.makeText(this@AdminHomeActivity, message, Toast.LENGTH_LONG).show()

            /**reset the password since all DB would be cleared**/
            DBHelper.newAdminPassword(password)
        }
    }
}
