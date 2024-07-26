package com.southernsunrise.drizzle.data.remote.models.responseWeatherModel

import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AstronomicalPropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AtmosphericPropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.DailyWeatherDataModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.TemperaturePropertiesModel
import com.southernsunrise.drizzle.utils.Constants.getWeatherMedia
import com.southernsunrise.drizzle.utils.clipSeconds

data class Day(
    val cloudcover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Long,
    val description: String,
    val dew: Double,
    val feelslike: Double,
    val feelslikemax: Double,
    val feelslikemin: Double,
    val hours: List<Hour>,
    val humidity: Double,
    val icon: String,
    val moonphase: Double,
    val precip: Double,
    val precipcover: Double,
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
    val sunrise: String,
    val sunriseEpoch: Long,
    val sunset: String,
    val sunsetEpoch: Long,
    val temp: Double,
    val tempmax: Double,
    val tempmin: Double,
    val uvindex: Double,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
) {
    fun toDailyWeatherDataModel():DailyWeatherDataModel {
        return DailyWeatherDataModel(
            dateTime = datetime,
            temperaturePropertiesModel = TemperaturePropertiesModel(
                tempAverage = temp,
                tempMin = tempmin,
                tempMax = tempmax,
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
            astronomicalPropertiesModel = AstronomicalPropertiesModel(
                sunriseTimeSting = clipSeconds(sunrise),
                sunsetTimeString = clipSeconds(sunset),
                moonPhase = moonphase
            ),
            weatherCondition = conditions,
            hours = hours.map { it -> it.toHourlyWeatherDataModel() },
            weatherMediaId = icon
        )
    }
}