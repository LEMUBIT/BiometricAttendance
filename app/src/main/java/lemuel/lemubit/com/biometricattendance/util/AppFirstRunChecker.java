package lemuel.lemubit.com.biometricattendance.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppFirstRunChecker {
    public static Boolean isFirstRun(Activity activity) {

        SharedPreferences mSettings = activity.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if (mSettings.getBoolean("firstrun", true)) {
            mSettings.edit().putBoolean("firstrun", false).apply();
            return true;
        } else {
            return false;
        }
    }

    //Allows Admin enter any password when login in and uses that as new password
    public static void refreshApp (Activity activity){
        SharedPreferences mSettings = activity.getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
            mSettings.edit().putBoolean("firstrun", true).apply();
    }
}
