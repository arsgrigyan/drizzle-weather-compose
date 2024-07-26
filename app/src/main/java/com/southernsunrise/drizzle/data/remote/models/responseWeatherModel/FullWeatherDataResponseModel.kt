package com.southernsunrise.drizzle.data.remote.models.responseWeatherModel

import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AstronomicalPropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AtmosphericPropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.CurrentWeatherDataModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.FifteenDaysWeatherDataModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.TemperaturePropertiesModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.WeatherModel
import com.southernsunrise.drizzle.utils.Constants.getWeatherMedia

data class FullWeatherDataResponseModel(
    val address: String,
    val alerts: List<Any>,
    val currentConditions: CurrentConditions,
    val days: List<Day>,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val queryCost: Int,
    val resolvedAddress: String,
    val stations: Stations,
    val timezone: String,
    val tzoffset: Double
) {
    fun toLocationFullWeatherModel(): LocationFullWeatherModel {
        return LocationFullWeatherModel(
            address = resolvedAddress,
            latitude = latitude,
            longitude = longitude,
            lastUpdated = currentConditions.datetimeEpoch,
            timeZone = timezone,
            WeatherModel(
                currentWeatherDataModel = CurrentWeatherDataModel(
                    dateTimeString = currentConditions.datetime,
                    datetimeEpoch = currentConditions.datetimeEpoch,
                    temperaturePropertiesModel = TemperaturePropertiesModel(
                        tempAverage = currentConditions.temp,
                        tempMin = days[0].tempmin,
                        tempMax = days[0].tempmax,
                        tempFeelsLike = currentConditions.feelslike
                    ),
                    atmosphericPropertiesModel = AtmosphericPropertiesModel(
                        windSpeed = currentConditions.windspeed,
                        windDirection = currentConditions.winddir,
                        windGust = currentConditions.windgust,
                        humidity = currentConditions.humidity,
                        visibility = currentConditions.visibility,
                        pressure = currentConditions.pressure,
                        precipitation = currentConditions.precip,
                        precipitationProbability = currentConditions.precipprob,
                        precipitationType = currentConditions.preciptype,
                        cloudCover = currentConditions.cloudCover,
                        dew = currentConditions.dew,
                        snow = currentConditions.snow,
                        snowDepth = currentConditions.snowdepth,
                        uvIndex = currentConditions.uvindex,
                        severeRisk = days[0].severerisk
                    ),
                    astronomicalPropertiesModel = AstronomicalPropertiesModel(
                        sunriseTimeSting = days[0].sunrise,
                        sunsetTimeString = days[0].sunset,
                        moonPhase = days[0].moonphase
                    ),
                    weatherCondition = currentConditions.conditions,
                    weatherMediaId = currentConditions.icon

                ),
                fifteenDaysWeatherDataModel = FifteenDaysWeatherDataModel(
                    days = days.map { it: Day -> it.toDailyWeatherDataModel() }
                )
            )

        )
    }
}