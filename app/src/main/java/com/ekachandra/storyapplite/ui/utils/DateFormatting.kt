package com.ekachandra.storyapplite.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.withDateFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.US)
    val date = inputFormat.parse(this) as Date
    return outputFormat.format(date)
}