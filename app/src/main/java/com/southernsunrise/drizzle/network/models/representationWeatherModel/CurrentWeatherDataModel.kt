package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class CurrentWeatherDataModel(
    val dateTimeString: String,
    val datetimeEpoch:Long,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val astronomicalPropertiesModel: AstronomicalPropertiesModel,
    val weatherCondition: String,
    val weatherMedia:WeatherMedia
)
