package lemuel.lemubit.com.biometricattendance.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapzen.speakerbox.Speakerbox
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
    private lateinit var registrationLeftHandListener: RegistrationLeftHandListener
    private lateinit var iuiOperations: IUIOperations
    private var allFingersCaptured = false
    lateinit var currentContext: Context
    lateinit var speakerbox: Speakerbox
    var currentFinger = Fingers.THUMB

    //maps the finger count to the ID of the fingerprint
    // so <1,44> is for the first finger that has an ID of 44
    var fingerIDMap = HashMap<Int, Int>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        registrationLeftHandListener = (context as RegistrationLeftHandListener?)!!
        iuiOperations = (context as IUIOperations)
        currentContext = this.activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_left_hand, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speakerbox = Speakerbox(activity?.application)
        speakerbox.enableVolumeControl(activity)

        btn_capture_left_fingers.setOnClickListener {
            if (!allFingersCaptured) {
                playInstruction()
                NativeSensor.getAndSaveFingerprint(currentContext, iuiOperations, this).subscribe(observer)
            } else {
                speakerbox.play(getString(R.string.proceed_with_registration))
            }
        }

        btn_leftHandFrag_next.setOnClickListener {
            registrationLeftHandListener.onLeftHandRegistered(fingerIDMap)
        }
    }

    override fun updateFingerPrintImage(fi: FingerprintImage?) {
        activity?.runOnUiThread {
            val bitmap = Fingers.getBitmap(activity, fi)
            image_left_fingerprint.setImageBitmap(bitmap)
        }
    }

    private val observer = object : Observer<Int> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(id: Int) {
            if (Fingers.hasValidId(id)) {
                fingerIDMap.put(currentFinger, id)
                fingerPrintGotten(currentFinger)
                nextFinger()
            } else {
                tellUserToTryAgain()
            }
        }

        override fun onError(e: Throwable) {
            tellUserToTryAgain()
        }

    }

    private fun playInstruction() {
        when (currentFinger) {
            Fingers.THUMB -> speakerbox.play(getString(R.string.place_left_thumb))
            Fingers.INDEX_FINGER -> speakerbox.play(getString(R.string.place_left_index))
            Fingers.MIDDLE_FINGER -> speakerbox.play(getString(R.string.place_left_middle))
            Fingers.RING_FINGER -> speakerbox.play(getString(R.string.place_left_ring))
            Fingers.PINKY_FINGER -> speakerbox.play(getString(R.string.place_left_pinky))
        }
    }

    private fun nextFinger() {
        if (currentFinger < Fingers.PINKY_FINGER) {
            currentFinger++
        } else {
            allFingersCaptured = true
            enableNext()
        }
    }

    private fun fingerPrintGotten(currentFinger: Int) {
        speakerbox.play(getString(R.string.fingerprint_gotten))
        when (currentFinger) {
            Fingers.THUMB -> animation_left_thumb.visibility = View.VISIBLE
            Fingers.INDEX_FINGER -> animation_left_index.visibility = View.VISIBLE
            Fingers.MIDDLE_FINGER -> animation_left_middle.visibility = View.VISIBLE
            Fingers.RING_FINGER -> animation_left_ring.visibility = View.VISIBLE
            Fingers.PINKY_FINGER -> animation_left_pinky.visibility = View.VISIBLE
        }
    }

    private fun tellUserToTryAgain() {
        speakerbox.play(getString(R.string.try_again))
    }

    private fun enableNext() {
        btn_leftHandFrag_next.isEnabled = true
    }

    interface RegistrationLeftHandListener {
        fun onLeftHandRegistered(fingerPrintIdMap: HashMap<Int, Int>)
    }
}
