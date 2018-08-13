package lemuel.lemubit.com.biometricattendance.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatHelper {
    fun formatDateToDayMonthYear(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        val format1 = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        return format1.format(cal.time)
    }

    fun getDayOfMonth(): Int {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        return day;
    }

    fun getMonth(): Int {
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        return month
    }

    fun getYear():Int
    {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        return year;
    }



}
