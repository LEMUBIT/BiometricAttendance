package lemuel.lemubit.com.biometricattendance.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class ProgressDialogHelper {

    public static MaterialDialog.Builder getBuilder(Context context, String title, String content) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .progressIndeterminateStyle(true);
    }
}
