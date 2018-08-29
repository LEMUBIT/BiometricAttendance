package lemuel.lemubit.com.biometricattendance.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {
    val currentTime: String
        get() {
            val currentTime = SimpleDateFormat("hh:mm:ss a", Locale.UK)
            return currentTime.format(Date())
        }

    val currentDate: String
        get() {
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
            return currentDate.format(Date())
        }

    @Throws(ParseException::class)
    fun isPassedDeadline(registeredTime: String): Boolean {
        val parser = SimpleDateFormat("hh:mm:ss a", Locale.UK)
        val mCurrentTime = parser.parse(registeredTime)
        val mDeadline = parser.parse("08:14:59 am")

        return mCurrentTime.after(mDeadline)
    }

     fun formatDate(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        val format1 = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        val formatted = format1.format(cal.time)

        return formatted
    }
}
