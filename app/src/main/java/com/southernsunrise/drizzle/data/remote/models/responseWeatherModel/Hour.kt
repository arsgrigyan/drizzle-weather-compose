package com.southernsunrise.drizzle.data.remote.models.responseWeatherModel

import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AtmosphericPropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.HourlyWeatherDataModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.TemperaturePropertiesModel
import com.southernsunrise.drizzle.utils.clipSeconds

data class Hour(
    val cloudcover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Long,
    val dew: Double,
    val feelslike: Double,
    val humidity: Double,
    val icon: String,
    val precip: Double,
    val precipprob: Double,
    val preciptype: List<String>,
    val pressure: Double,
    val severerisk: Double,
    val snow: Double,
    val snowdepth: Double,
    val solarenergy: Double,
    val solarradiation: Double,
    val source: String,
    val stations: List<String>,
    val temp: Double,
    val uvindex: Double,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
) {
    fun toHourlyWeatherDataModel(): HourlyWeatherDataModel {
        return HourlyWeatherDataModel(
            dateTime = clipSeconds(datetime),
            datetimeEpoch = datetimeEpoch,
            temperaturePropertiesModel = TemperaturePropertiesModel(
                tempAverage = temp,
                tempMin = null,
                tempMax = null,
                tempFeelsLike = feelslike
            ),
            atmosphericPropertiesModel = AtmosphericPropertiesModel(
                windSpeed = windspeed,
                windDirection = winddir,
                windGust = windgust,
                humidity = humidity,
                visibility = visibility,
                pressure = pressure,
                precipitation = precip,
                precipitationProbability = precipprob,
                precipitationType = preciptype,
                cloudCover = cloudcover,
                dew = dew,
                snow = snow,
                snowDepth = snowdepth,
                uvIndex = uvindex,
                severeRisk = severerisk
            ),
            conditions = conditions,
            weatherMediaId = icon
        )
    }
}