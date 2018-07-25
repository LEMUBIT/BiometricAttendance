package lemuel.lemubit.com.biometricattendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rollbar.android.Rollbar;

import lemuel.lemubit.com.biometricattendance.nativeFeatures.NativeSensor;
import lemuel.lemubit.com.biometricattendance.view.IUIOperations;

public class MainActivity extends AppCompatActivity implements IUIOperations {
    MaterialDialog materialDialog;
    MaterialDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressDialog();
        Rollbar.init(this);
        NativeSensor.INSTANCE.init(this, this);
        throw new RuntimeException();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgressDialog(String title, String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                materialDialog.show();
            }
        });
    }

    @Override
    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                materialDialog.dismiss();
            }
        });
    }

    @Override
    public void setProgressDialog() {
        dialogBuilder = new MaterialDialog.Builder(this)
                .title(R.string.enrol)
                .content(R.string.press_finger)
                .progress(true, 0)
                .progressIndeterminateStyle(true);

        materialDialog = dialogBuilder.build();
    }

    @Override
    public void showInfoToast(String info) {
        Toast.makeText(this, "Message: "+info, Toast.LENGTH_SHORT).show();
    }
}
