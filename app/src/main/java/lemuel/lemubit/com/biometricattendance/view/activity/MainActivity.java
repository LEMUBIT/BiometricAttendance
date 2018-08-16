package lemuel.lemubit.com.biometricattendance.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rollbar.android.Rollbar;

import io.realm.Realm;
import lemuel.lemubit.com.biometricattendance.R;
import lemuel.lemubit.com.biometricattendance.model.DBHelper;
import lemuel.lemubit.com.biometricattendance.util.AdminDialogHelper;
import lemuel.lemubit.com.biometricattendance.util.DBOperation;
import lemuel.lemubit.com.biometricattendance.util.ProgressDialogHelper;
import lemuel.lemubit.com.biometricattendance.view.AdminLogin;
import lemuel.lemubit.com.biometricattendance.view.IUIOperations;

public class MainActivity extends AppCompatActivity implements IUIOperations, AdminLogin {
    MaterialDialog progressMaterialDialog;
    MaterialDialog adminMaterialDialog;

    //TODO: remove code not needed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressDialog();
        Realm.init(this);
        Rollbar.init(this);
        testCode();
        // NativeSensor.INSTANCE.init(this, this);
        setAdminLoginDialog();
    }

    //TODO remove when done
    private void testCode() {
      // DBOperation dbOperation= DBHelper.newAdminPassword("love");
//       if(DBHelper.newAdminPassword("love")==DBOperation.SUCCESSFUL)
//       {
//           Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
//       }
//       else
//       {
//           Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
//       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_admin:
                adminMaterialDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgressDialog(String title, String message) {
        runOnUiThread(() -> progressMaterialDialog.show());
    }

    @Override
    public void dismissProgressDialog() {
        runOnUiThread(() -> progressMaterialDialog.dismiss());
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
    public void adminLoginSuccess() {
        startActivity(new Intent(this, AdminHomeActivity.class));
    }

    @Override
    public void adminLoginFailed() {
        Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
    }

    public void setAdminLoginDialog() {
        adminMaterialDialog = AdminDialogHelper.getBuilder(this, this).build();
    }
}
