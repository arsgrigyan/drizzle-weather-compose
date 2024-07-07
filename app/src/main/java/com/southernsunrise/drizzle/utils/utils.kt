package com.southernsunrise.drizzle.utils

import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.southernsunrise.drizzle.network.models.representationWeatherModel.HourlyWeatherDataModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun getScreenDimensInDp(): Pair<Dp, Dp> {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    return Pair(screenWidth, screenHeight)

}

@Composable
fun getScreenHeightFraction(fraction: Float): Dp {
    return getScreenDimensInDp().second * fraction
}


fun clipSeconds(time: String): String {
    // Ensure the input string is in the expected format "HH:mm:ss"
    if (time.length != 8 || time[2] != ':' || time[5] != ':') {
        throw IllegalArgumentException("Time must be in the format HH:mm:ss")
    }
    return time.substring(0, 5)
}

fun updatedAgo(updateTimeSeconds: Long): String {
    val differenceMillis: Long = System.currentTimeMillis() - updateTimeSeconds * 1000

    val seconds = ((differenceMillis / 1000) % 60).toInt()
    val minutes = (differenceMillis / (1000 * 60) % 60).toInt()
    val hours = (differenceMillis / (1000 * 60 * 60) % 24).toInt()
    val days = (differenceMillis / (1000 * 60 * 60 * 24) % 365.25).toInt()
    val years = (differenceMillis / (1000L * 60 * 60 * 24 * 365.25)).toInt()

    val updatedAgo: StringBuilder = StringBuilder()
    updatedAgo.apply {
        append("Updated ")
        if (years == 0) {
            if (days == 0) {
                if (hours == 0) {
                    if (minutes == 0) {
                        append("just now")
                    } else append("$minutes minutes ago")
                } else {
                    if (hours == 1) {
                        if (minutes == 0) append("an hour ago")
                        else append("1hr ${minutes}mins ago")
                    } else append("${hours} hours $minutes minutes ago")
                }
            } else {
                if (hours == 0) append("$days days ago")
                else if (hours == 1) append("$days days an hour ago")

            }
        } else append("$years years ago")
    }

    return updatedAgo.toString()

}

fun extractHourStringFromTimeString(time: String): String {
    return time.substring(0, 2)
}

fun List<HourlyWeatherDataModel>.sliceFromCurrentHour(hourString: String): List<HourlyWeatherDataModel> {
    var currentHourIndex = 0
    for (i in this.indices) {
        if (extractHourStringFromTimeString(this[i].dateTime) == hourString) {
            currentHourIndex = i
            break
        }
    }

    val newHourlyModelsList = mutableListOf<HourlyWeatherDataModel>()

    return if (currentHourIndex != 0) {
        for (i in currentHourIndex..<this.size) {
            newHourlyModelsList.add(this[i])
        }
        newHourlyModelsList
    } else this
}


fun getDateStringFromEpochSecondsLong(epochSeconds: Long): String {
    val date = Date(epochSeconds * 1000)
    val c: Calendar = Calendar.getInstance()
    c.time = date

    return c.getDisplayName(Calendar.DAY_OF_WEEK_IN_MONTH, Calendar.LONG, Locale.US)?.toString()
        ?: "unknown time"
}

fun String.formatAsWeekMonthDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())

    val date: Date? = inputFormat.parse(this)
    val outputDateStr: String = date?.let { outputFormat.format(it) } ?: "unknown date"

    return outputDateStr
}

fun locationServicesEnabled(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // This is a new method provided in API 28
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.isLocationEnabled
    } else {
        // This was deprecated in API 28
        val mode: Int = Settings.Secure.getInt(
            context.contentResolver, Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF
        )
        (mode != Settings.Secure.LOCATION_MODE_OFF)
    }
}


@Composable
fun openURL(url: String) {
    val uriHandler = LocalUriHandler.current
    uriHandler.openUri(url)
}