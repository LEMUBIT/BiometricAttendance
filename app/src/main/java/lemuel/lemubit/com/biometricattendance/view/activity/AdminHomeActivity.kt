package lemuel.lemubit.com.biometricattendance.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_home.*
import lemuel.lemubit.com.biometricattendance.R

class AdminHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        btn_newcapture.setOnClickListener {
            startActivity(Intent(this, NewCaptureActivity::class.java))
        }

        btn_viewUsers.setOnClickListener {
           startActivity( Intent(this, ViewUsersActivity::class.java))
        }

        btn_viewAttendance.setOnClickListener {
            startActivity( Intent(this, ViewAttendanceActivity::class.java))
        }
    }
}
