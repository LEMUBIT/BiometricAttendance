package lemuel.lemubit.com.biometricattendance.view.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_registration_final.*
import lemuel.lemubit.com.biometricattendance.R

//todo show fingerPrint images
class RegistrationFinalFragment : Fragment() {
    lateinit var registrationFinalListener: RegistrationFinalListener
    private var firstName: String? = null
    private var lastName: String? = null
    private var sex: String? = null
    private var dateOfBirth: String? = null
    private var email: String? = null
    private var phoneNumber: String? = null
    private var capturedPhotoByteArray: ByteArray? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        registrationFinalListener = (context as RegistrationFinalListener?)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstName = arguments?.getString("firstName")
        lastName = arguments?.getString("lastName")
        sex = arguments?.getString("sex")
        dateOfBirth = arguments?.getString("dateOfBirth")
        email = arguments?.getString("email")
        phoneNumber = arguments?.getString("phoneNumber")
        capturedPhotoByteArray = arguments?.getByteArray("capturedPhotoByteArray")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_final, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_reg_final_firstname.setText(firstName)
        txt_reg_final_lastname.setText(lastName)
        txt_reg_final_sex.setText(sex)
        txt_reg_final_birthDate.setText(dateOfBirth)
        txt_reg_final_email.setText(email)
        txt_reg_final_phone_number.setText(phoneNumber)
        val bitmap = BitmapFactory.decodeByteArray(capturedPhotoByteArray, 0, capturedPhotoByteArray?.size!!)
        img_reg_final_picture.setImageBitmap(bitmap)

        btn_reg_finish.setOnClickListener {
            registrationFinalListener.onUserInfoConfirmed()
        }
    }

    interface RegistrationFinalListener {
        fun onUserInfoConfirmed()
    }
}
