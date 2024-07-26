package com.southernsunrise.drizzle.data.remote.models.responseWeatherModel

import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.CurrentWeatherDataModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.TemperaturePropertiesModel

data class CurrentConditions(
    val cloudCover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Long,
    val dew: Double,
    val feelslike: Double,
    val humidity: Double,
    val icon: String,
    val moonphase: Double,
    val precip: Double,
    val precipprob: Double,
    val preciptype: List<String>?,
    val pressure: Double,
    val snow: Double,
    val snowdepth: Double,
    val solarenergy: Double,
    val solarradiation: Double,
    val source: String,
    val stations: List<String>,
    val sunrise: String,
    val sunriseEpoch: Long,
    val sunset: String,
    val sunsetEpoch: Long,
    val temp: Double,
    val uvindex: Double,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
)