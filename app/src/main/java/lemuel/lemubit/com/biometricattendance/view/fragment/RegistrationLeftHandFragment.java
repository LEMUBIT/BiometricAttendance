package lemuel.lemubit.com.biometricattendance.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lemuel.lemubit.com.biometricattendance.R;

public class RegistrationLeftHandFragment extends Fragment {
    RegistrationLeftHandListener registrationLeftHandListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registrationLeftHandListener = (RegistrationLeftHandListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_left_hand, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface RegistrationLeftHandListener {
        void onLeftHandRegistered();
    }
}
