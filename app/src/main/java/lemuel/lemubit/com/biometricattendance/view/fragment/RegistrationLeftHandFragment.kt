package lemuel.lemubit.com.biometricattendance.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wepoy.fp.FingerprintImage
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_registration_left_hand.*
import lemuel.lemubit.com.biometricattendance.R
import lemuel.lemubit.com.biometricattendance.nativeFeatures.NativeSensor
import lemuel.lemubit.com.biometricattendance.util.Fingers
import lemuel.lemubit.com.biometricattendance.view.IFingerPrintOperation
import lemuel.lemubit.com.biometricattendance.view.IUIOperations

class RegistrationLeftHandFragment : Fragment(), IFingerPrintOperation {
    internal lateinit var registrationLeftHandListener: RegistrationLeftHandListener
    internal lateinit var iuiOperations: IUIOperations
    var currentFinger = Fingers.THUMB
    lateinit var currentContext: Context
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        registrationLeftHandListener = (context as RegistrationLeftHandListener?)!!
        iuiOperations = (context as IUIOperations)
        currentContext = this!!.activity!!

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_left_hand, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_capture_left_fingers.setOnClickListener {
            NativeSensor.getFingerPrintDataObservable(currentContext, iuiOperations, this)//TODO continue from here
        }
    }

    override fun updateFingerPrintImage(fi: FingerprintImage?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val observer = object : Observer<Int> {
        override fun onComplete() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSubscribe(d: Disposable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNext(t: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(e: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    interface RegistrationLeftHandListener {
        fun onLeftHandRegistered()
    }
}
