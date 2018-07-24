package lemuel.lemubit.com.biometricattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rollbar.android.Rollbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rollbar.init(this);
    }
}
