package com.southernsunrise.drizzle.data.remote.models.representationWeatherModel

data class HourlyWeatherDataModel(
    val dateTime: String,
    val datetimeEpoch: Long,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val conditions: String,
    val weatherMediaId: String
)
