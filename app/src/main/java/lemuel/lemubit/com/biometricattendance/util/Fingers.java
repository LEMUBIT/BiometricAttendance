package lemuel.lemubit.com.biometricattendance.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;

import com.wepoy.fp.FingerprintImage;

import lemuel.lemubit.com.biometricattendance.R;

public class Fingers {
    public static int THUMB=1;
    public static int INDEX_FINGER=2;
    public static int MIDDLE_FINGER=3;
    public static int RING_FINGER=4;
    public static int PINKY_FINGER=5;
    public static int ALL_FINGERS_CAPTURED = 5;
    public static String FINGER_ID_HASHMAP_BUNDLE="FingerIDHashMap";
    public static int FIRST_CAPTURE=1;
    public static int SECOND_CAPTURE=2;
    public static int THIRD_CAPTURE=3;

    public static Bitmap getBitmap(FragmentActivity context, FingerprintImage fi) {
        byte[] fpBmp = null;
        Bitmap bitmap;
        if (fi == null || (fpBmp = fi.convert2Bmp()) == null || (bitmap = BitmapFactory.decodeByteArray(fpBmp, 0, fpBmp.length)) == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.nofinger);
        }

        return bitmap;
    }

    public static Boolean hasValidId(int Id)
    {
        return Id >= 0;
    }
}
