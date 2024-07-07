package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class DailyWeatherDataModel(
    val dateTime:String,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val astronomicalPropertiesModel: AstronomicalPropertiesModel,
    val weatherCondition: String,
    val hours: List<HourlyWeatherDataModel>,
    val weatherMedia: WeatherMedia
)
