package lemuel.lemubit.com.biometricattendance.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.model.AttendanceDb
import lemuel.lemubit.com.biometricattendance.model.DBHelper

class ViewAttendanceAdapter internal constructor(data: RealmResults<AttendanceDb>, var context: Context) : RealmRecyclerViewAdapter<AttendanceDb, ViewAttendanceAdapter.MyViewHolder>(data, true) {

     init {
        context = context.applicationContext
         TODO("Implement feature to check if deadline is passed, can be a static time for now, can use colour or Lottie")
    }


    internal fun newRecord() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.user_attendance_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val attendance = getItem(position)

        val userPhotoByte = DBHelper.getUserImage(attendance?.id!!)
        val userPhotobitmap = BitmapFactory.decodeByteArray(userPhotoByte, 0, userPhotoByte.size)
        holder?.userImage?.setImageBitmap(userPhotobitmap)

        val name = DBHelper.getUserName(attendance?.id!!)
        holder?.userName?.text = name

        holder?.clockTime?.text = attendance.time

        holder?.clockStateImage?.setImageDrawable(getDirectionDrawable(attendance.clockState))
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userImage: ImageView
        var userName: TextView
        var clockTime: TextView
        var clockStateImage: ImageView

        init {
            userImage = view.findViewById(R.id.img_row_attendance_userImage)
            userName = view.findViewById(R.id.txt_row_attendance_userName)
            clockTime = view.findViewById(R.id.txt_row_attendance_clockTime)
            clockStateImage = view.findViewById(R.id.img_row_attendance_clockDirection)
        }
    }

    private fun getDirectionDrawable(state: Int): Drawable {
        if (state == 1) {
            return context.resources.getDrawable(R.drawable.sign_in_arrow_64dp)
        } else {
            return context.resources.getDrawable(R.drawable.sign_out_arrow_64dp)
        }
    }
}
