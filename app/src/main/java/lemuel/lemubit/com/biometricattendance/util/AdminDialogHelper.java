package lemuel.lemubit.com.biometricattendance.util;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import lemuel.lemubit.com.biometricattendance.R;
import lemuel.lemubit.com.biometricattendance.model.DBHelper;
import lemuel.lemubit.com.biometricattendance.view.AdminLogin;

public class AdminDialogHelper {
    public static MaterialDialog.Builder getBuilder(Activity context, final AdminLogin adminLogin) {
        boolean wrapInScrollView = true;
        final Activity[] activity = new Activity[1];
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("Admin Login")
                .customView(R.layout.activity_admin_login, wrapInScrollView)
                .positiveText("Login")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    TextView passwordTextView = dialog.getView().findViewById(R.id.txt_password);
                    try {
                        activity[0] = (Activity) context;
                    } catch (Exception e) {

                    }

                    if (firstRunAndNotEmpty(activity[0], passwordTextView)) {
                        DBOperation dbOperation = DBHelper.newAdminPassword(passwordTextView.getText().toString());
                        if (dbOperation == DBOperation.SUCCESSFUL) {
                            adminLogin.adminLoginSuccess(passwordTextView.getText().toString());
                        } else {
                            adminLogin.adminLoginFailed();
                        }

                    } else if (notFirstRunAndNotEmpty(activity[0], passwordTextView)) {
                        DBOperation dbOperation = DBHelper.loginAdmin(passwordTextView.getText().toString());
                        if (dbOperation == DBOperation.SUCCESSFUL) {
                            adminLogin.adminLoginSuccess(passwordTextView.getText().toString());
                        } else {
                            adminLogin.adminLoginFailed();
                        }
                    }
                    passwordTextView.setText("");
                })
                .onNegative((dialog, which) -> dialog.dismiss());
        return builder;
    }

    private static boolean firstRunAndNotEmpty(Activity activity, TextView txtPassword) {
        return AppFirstRunChecker.isFirstRun(activity) && !TextUtils.isEmpty(txtPassword.getText().toString());
    }

    private static boolean notFirstRunAndNotEmpty(Activity activity, TextView txtPassword) {
        return !AppFirstRunChecker.isFirstRun(activity) && !TextUtils.isEmpty(txtPassword.getText().toString());
    }
}
