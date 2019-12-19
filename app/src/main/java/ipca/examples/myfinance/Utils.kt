package ipca.examples.myfinance

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun dateToString(date: Date) : String{
    val format = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val dateStr = format.format(date)
    return dateStr
}

//stringToDateToLocalTime
fun stringToDate(rawDate: String): Date {
    var dateTime = Date()

    try {
        val format = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        dateTime = format.parse(rawDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return dateTime
}
