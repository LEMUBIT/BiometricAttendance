package lemuel.lemubit.com.biometricattendance.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rollbar.android.Rollbar;

import lemuel.lemubit.com.biometricattendance.R;
import lemuel.lemubit.com.biometricattendance.util.AdminDialogHelper;
import lemuel.lemubit.com.biometricattendance.util.ProgressDialogHelper;

public class MainActivity extends AppCompatActivity implements IUIOperations, AdminLogin {
    MaterialDialog progressMaterialDialog;
    MaterialDialog adminMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressDialog();
        Rollbar.init(this);
        // NativeSensor.INSTANCE.init(this, this);
        setAdminLoginDialog();
        adminMaterialDialog.show();
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
                progressMaterialDialog.show();
            }
        });
    }

    @Override
    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressMaterialDialog.dismiss();
            }
        });
    }

    @Override
    public void setProgressDialog() {
        progressMaterialDialog = ProgressDialogHelper.getBuilder(this,
                getResources().getString(R.string.enrol),
                getResources().getString(R.string.press_finger))
                .build();
    }

    @Override
    public void showInfoToast(String info) {
        Toast.makeText(this, "Message: " + info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AdminLoginSuccess() {

    }

    @Override
    public void AdminLoginFailed() {

    }

    public void setAdminLoginDialog() {
        adminMaterialDialog = AdminDialogHelper.getBuilder(this, this).build();
    }
}
