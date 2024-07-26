package com.southernsunrise.drizzle.data.remote.models.representationWeatherModel

data class CurrentWeatherDataModel(
    val dateTimeString: String,
    val datetimeEpoch: Long,
    val temperaturePropertiesModel: TemperaturePropertiesModel,
    val atmosphericPropertiesModel: AtmosphericPropertiesModel,
    val astronomicalPropertiesModel: AstronomicalPropertiesModel,
    val weatherCondition: String,
    val weatherMediaId:String
)
