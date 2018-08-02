package lemuel.lemubit.com.biometricattendance.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lemuel.lemubit.com.biometricattendance.R;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //todo:: add activity with fragment which would handle registration flow step by step
        //todo:: finish then click next to move to next fragment
    }
}
