package lemuel.lemubit.com.biometricattendance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.model.UserInformationDb

class ViewUsersAdapter internal constructor(data: RealmResults<UserInformationDb>, var context: Context) : RealmRecyclerViewAdapter<UserInformationDb, ViewUsersAdapter.MyViewHolder>(data, true) {

    init {
        context = context.applicationContext
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.user_info_row, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.userName.text = """${user?.firstName} ${user?.lastName}"""
        holder.userPhone.text = user?.phoneNumber
        val bitmap = BitmapFactory.decodeByteArray(user?.userPhoto, 0, user?.userPhoto?.size!!)
        holder.userImage.setImageBitmap(bitmap)
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userName: TextView
        var userPhone: TextView
        var userImage: ImageView

        init {
            userName = view.findViewById(R.id.txt_row_userName)
            userPhone = view.findViewById(R.id.txt_row_userPhoneNo)
            userImage = view.findViewById(R.id.img_row_userImage)
        }
    }
}
