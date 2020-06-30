package com.romariomkk.gitrepo.util

import com.romariomkk.gitrepo.R
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    var DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    var serverToLocalDateFormatter = SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH)
    var shortDateFormatter = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)

    var LANGUAGE_COLOR_MAP: Map<String, Int> =
        mapOf(
            "Java" to android.R.color.holo_red_dark,
            "Kotlin" to R.color.color_blue,
            "Dart" to android.R.color.holo_green_light,
            "JavaScript" to R.color.color_orange,
            "CSS" to R.color.color_yellow,
            "Ruby" to android.R.color.holo_red_light,
            "Objective-C" to android.R.color.holo_purple
        )


    fun getDate(dateString: String): String {
        val date: Date? = serverToLocalDateFormatter.parse(dateString)
        return date?.let {
            shortDateFormatter.format(it)
        } ?: "xx"
    }

}