package lemuel.lemubit.com.biometricattendance.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.adapter.ViewUsersAdapter
import lemuel.lemubit.com.biometricattendance.model.DBHelper

class ViewUsersActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: ViewUsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_users)
        recyclerView = findViewById(R.id.recyclerUserViews)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = ViewUsersAdapter(DBHelper.getUsers(), this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        recyclerView?.setHasFixedSize(true)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
