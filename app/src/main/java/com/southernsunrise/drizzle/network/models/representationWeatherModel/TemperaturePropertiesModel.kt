package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class TemperaturePropertiesModel(
    val tempAverage: Double,
    // hourly forecast model does not have tempMax and temMin hence they are nullable
    val tempMin: Double?,
    val tempMax: Double?,
    val tempFeelsLike: Double,
)
