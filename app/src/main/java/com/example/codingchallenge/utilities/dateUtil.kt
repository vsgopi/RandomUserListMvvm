package com.example.codingchallenge.utilities

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatUtil {

    fun dobFormat(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate: Date = inputFormat.parse(date) ?: return ""
        return outputFormat.format(parsedDate)
    }
}