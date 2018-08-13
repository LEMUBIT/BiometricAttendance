package lemuel.lemubit.com.biometricattendance.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import lemuel.lemubit.com.biometricattendance.R;
import lemuel.lemubit.com.biometricattendance.util.DateFormatHelper;

import static android.text.TextUtils.isEmpty;

public class RegistrationInfoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    RegistrationInfoListener registrationInfoListener;
    Boolean dateIsSet = false;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phoneNumber;
    int successfulValidation = 1;
    int failedValidation = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registrationInfoListener = (RegistrationInfoListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstName = view.findViewById(R.id.txt_reg_firstname);
        lastName = view.findViewById(R.id.txt_reg_lastname);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        TextView datePicker = view.findViewById(R.id.txt_date_picker);
        datePicker.setOnClickListener(view1 -> {
            new DatePickerDialog(getContext(),
                    this,
                    DateFormatHelper.INSTANCE.getYear(),
                    DateFormatHelper.INSTANCE.getMonth(),
                    DateFormatHelper.INSTANCE.getDayOfMonth()).show();
        });

        email = view.findViewById(R.id.txt_reg_email);
        phoneNumber = view.findViewById(R.id.txt_reg_phone_number);
        Button btnNext = view.findViewById(R.id.btn_reg_next);

        btnNext.setOnClickListener(view12 -> {
            //todo: send complete user info
            if (validate() == successfulValidation)
                registrationInfoListener.onUserInfoGotten();
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        dateIsSet = true;
    }

    /**
     * @returns Integer indicating if all fields have been entered (1) or if not all fields have been entered (0)
     */
    private int validate() {
        return !isEmpty(firstName.getText().toString()) && !isEmpty(lastName.getText().toString()) && !isEmpty(email.getText().toString()) && !isEmpty(phoneNumber.getText().toString()) && dateIsSet ? successfulValidation : failedValidation;
    }

    public interface RegistrationInfoListener {
        void onUserInfoGotten();
    }
}
