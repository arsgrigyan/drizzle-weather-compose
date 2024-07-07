package com.southernsunrise.drizzle.utils

import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.network.models.representationWeatherModel.WeatherMedia
import com.southernsunrise.drizzle.ui.theme.verticalGradientClearSkyDay
import com.southernsunrise.drizzle.ui.theme.verticalGradientClearSkyNight
import com.southernsunrise.drizzle.ui.theme.verticalGradientCloudyDay
import com.southernsunrise.drizzle.ui.theme.verticalGradientCloudyNight
import com.southernsunrise.drizzle.ui.theme.verticalGradientFog
import com.southernsunrise.drizzle.ui.theme.verticalGradientRain
import com.southernsunrise.drizzle.ui.theme.verticalGradientThunderstorm
import com.southernsunrise.drizzle.ui.theme.verticalGradientWindSnowSleetHail

object Constants {

    const val VISUAL_CROSSING_URL = "https://www.visualcrossing.com/"
    const val MY_GITHUB_PAGE_URL = "https://github.com/arsgrigyan"

    private val weatherMediaMap = hashMapOf<String, WeatherMedia>(
        "snow" to WeatherMedia(
            appBackground = verticalGradientWindSnowSleetHail,
            weatherIcon = R.drawable.ic_snow
        ),
        "snow-showers-day" to WeatherMedia(
            appBackground = verticalGradientWindSnowSleetHail,
            weatherIcon = R.drawable.ic_snow_showers_day
        ),
        "snow-showers-night" to WeatherMedia(
            appBackground = verticalGradientWindSnowSleetHail,
            weatherIcon = R.drawable.ic_snow_showers_night
        ),
        "thunder-rain" to WeatherMedia(
            appBackground = verticalGradientThunderstorm,
            weatherIcon = R.drawable.ic_thunder
        ),
        "thunder-showers-day" to WeatherMedia(
            appBackground = verticalGradientThunderstorm,
            weatherIcon = R.drawable.ic_thunder_showers_day
        ),
        "thunder-showers-night" to WeatherMedia(
            appBackground = verticalGradientThunderstorm,
            weatherIcon = R.drawable.ic_thunder_showers_night
        ),
        "showers-day" to WeatherMedia(
            appBackground = verticalGradientRain,
            weatherIcon = R.drawable.ic_showers_day
        ),
        "showers-night" to WeatherMedia(
            appBackground = verticalGradientRain,
            weatherIcon = R.drawable.ic_showers_night
        ),
        "rain" to WeatherMedia(
            appBackground = verticalGradientThunderstorm,
            weatherIcon = R.drawable.ic_thunder
        ),
        "fog" to WeatherMedia(
            appBackground = verticalGradientFog,
            weatherIcon = R.drawable.ic_fog
        ),
        "wind" to WeatherMedia(
            appBackground = verticalGradientWindSnowSleetHail,
            weatherIcon = R.drawable.ic_wind
        ),
        "cloudy" to WeatherMedia(
            appBackground = verticalGradientCloudyDay,
            weatherIcon = R.drawable.ic_cloudy
        ),
        "partly-cloudy-day" to WeatherMedia(
            appBackground = verticalGradientCloudyDay,
            weatherIcon = R.drawable.ic_partly_cloudy_day
        ),
        "partly-cloudy-night" to WeatherMedia(
            appBackground = verticalGradientCloudyNight,
            weatherIcon = R.drawable.ic_partly_cloudy_night
        ),
        "clear-day" to WeatherMedia(
            appBackground = verticalGradientClearSkyDay,
            weatherIcon = R.drawable.ic_clear_day
        ),
        "clear-night" to WeatherMedia(
            appBackground = verticalGradientClearSkyNight,
            weatherIcon = R.drawable.ic_clear_night
        ),
    )

    fun getWeatherMedia(code:String):WeatherMedia{
        return weatherMediaMap[code]!!
    }
}
