package com.southernsunrise.drizzle.network.models.responseWeatherModel

import com.southernsunrise.drizzle.network.models.representationWeatherModel.AstronomicalPropertiesModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.AtmosphericPropertiesModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.CurrentWeatherDataModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.FifteenDaysWeatherDataModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.TemperaturePropertiesModel
import com.southernsunrise.drizzle.network.models.representationWeatherModel.WeatherModel
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
            resolvedAddress = resolvedAddress,
            latitude = latitude,
            longitude = longitude,
            datetimeEpoch = currentConditions.datetimeEpoch,
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
                    weatherMedia = getWeatherMedia(currentConditions.icon)

                ),
                fifteenDaysWeatherDataModel = FifteenDaysWeatherDataModel(
                    days = days.map { it: Day -> it.toDailyWeatherDataModel() }
                )
            )

        )
    }
}