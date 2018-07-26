package lemuel.lemubit.com.biometricattendance.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import lemuel.lemubit.com.biometricattendance.R;
import lemuel.lemubit.com.biometricattendance.view.AdminLogin;

public class AdminDialogHelper {
    public static MaterialDialog.Builder getBuilder(Context context, AdminLogin adminLogin) {
        boolean wrapInScrollView = true;
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("Admin Login")
                .customView(R.layout.activity_admin_login, wrapInScrollView)
                .positiveText("Login")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        TextView textView = dialog.getView().findViewById(R.id.txt_password);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        return builder;
    }
}
