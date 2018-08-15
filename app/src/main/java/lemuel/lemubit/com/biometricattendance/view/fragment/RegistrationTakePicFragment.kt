package lemuel.lemubit.com.biometricattendance.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_registration_takepicture.*

import lemuel.lemubit.com.biometricattendance.R
import java.io.ByteArrayOutputStream

class RegistrationTakePicFragment : Fragment() {
    private lateinit var registrationTakePicListener: RegistrationTakePicListener
    private lateinit var capturedPhotoByteArray: ByteArray
    private val REQUEST_IMAGE_CAPTURE = 0
    private var userImageCaptured = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        registrationTakePicListener = (context as RegistrationTakePicListener?)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_takepicture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_takePic_reg.setOnClickListener { takePicture() }

        btn_takePic_next.setOnClickListener {
            registrationTakePicListener.onUserPicGotten(capturedPhotoByteArray)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == RESULT_OK) {
                    val extras = data?.extras
                    val imageBitmap = extras?.get("data") as Bitmap
                    img_userImage.setImageBitmap(imageBitmap)
                    setImageByteArray(imageBitmap)
                    userImageCaptured = true
                    btn_takePic_next.isEnabled = true
                }
            }
        }
    }

    private fun setImageByteArray(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        capturedPhotoByteArray = byteArray
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity?.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    interface RegistrationTakePicListener {
        fun onUserPicGotten(capturedPhotoByteArray: ByteArray)
    }
}
