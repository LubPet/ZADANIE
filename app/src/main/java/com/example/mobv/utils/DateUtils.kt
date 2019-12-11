package com.example.mobv.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun parseTime(time: String, format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
            val formatter = SimpleDateFormat(format, Locale.US)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter.parse(time)
        }

        fun toString(date: Date) : String {
            val format = SimpleDateFormat("hh:mm:ss dd.MM.yyy", Locale.US)
            return format.format(date)
        }

    }
}