package lemuel.lemubit.com.biometricattendance.view.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.DatePicker
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_view_attendance.*
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.adapter.ViewAttendanceAdapter
import lemuel.lemubit.com.biometricattendance.model.AttendanceDb
import lemuel.lemubit.com.biometricattendance.model.DBHelper
import lemuel.lemubit.com.biometricattendance.util.TimeHelper
import java.util.*

class ViewAttendanceActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var recyclerView: RecyclerView? = null
    private var adapter: ViewAttendanceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attendance)
        recyclerView = findViewById(R.id.recyclerUserAttendance)
        setUpRecyclerView(DBHelper.getAttendance(TimeHelper.currentDate))

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_filter.setOnClickListener {
            DatePickerDialog(this@ViewAttendanceActivity, this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val formattedDate = TimeHelper.formatDate(p1, p2, p3)
        adapter = ViewAttendanceAdapter(DBHelper.getAttendance(formattedDate), this)
        recyclerView?.adapter = adapter
        adapter!!.newRecord()
    }


    private fun setUpRecyclerView(realmDateQuery: RealmResults<AttendanceDb>) {
        adapter = ViewAttendanceAdapter(realmDateQuery, this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        recyclerView?.setHasFixedSize(true)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
