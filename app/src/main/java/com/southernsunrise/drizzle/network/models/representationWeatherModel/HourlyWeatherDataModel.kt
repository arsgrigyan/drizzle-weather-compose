package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class HourlyWeatherDataModel(
    val dateTime:String,
    val datetimeEpoch: Long,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val conditions: String,
    val weatherMedia: WeatherMedia
)
