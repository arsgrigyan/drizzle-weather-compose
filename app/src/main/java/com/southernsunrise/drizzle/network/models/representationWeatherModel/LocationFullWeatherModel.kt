package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class LocationFullWeatherModel(
    val resolvedAddress:String,
    val latitude: Double,
    val longitude: Double,
    val datetimeEpoch:Long,
    val timeZone:String,
    val weatherModel: WeatherModel
)
