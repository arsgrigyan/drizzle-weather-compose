package com.southernsunrise.drizzle.data.remote.models.representationWeatherModel

data class DailyWeatherDataModel(
    val dateTime: String,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val astronomicalPropertiesModel: AstronomicalPropertiesModel,
    val weatherCondition: String,
    val hours: List<HourlyWeatherDataModel>,
    val weatherMediaId: String
)
