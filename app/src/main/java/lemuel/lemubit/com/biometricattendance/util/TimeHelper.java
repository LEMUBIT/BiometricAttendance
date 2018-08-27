package lemuel.lemubit.com.biometricattendance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {
    public static String getCurrentTime() {
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a", Locale.UK);
        return currentTime.format(new Date());
    }

    public static String getCurrentDate() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        return currentDate.format(new Date());
    }

    public static Boolean isPassedDeadline(String registeredTime) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("hh:mm:ss a", Locale.UK);
        Date mCurrentTime = parser.parse(registeredTime);
        Date mDeadline = parser.parse("08:14:59 am");

        return mCurrentTime.after(mDeadline);
    }
}
