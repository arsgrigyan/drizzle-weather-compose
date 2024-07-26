package com.southernsunrise.drizzle.data.remote.models.representationWeatherModel

import com.southernsunrise.drizzle.data.local.WeatherEntity

data class LocationFullWeatherModel(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val lastUpdated: Long,
    val timeZone: String,
    val weatherModel: com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.WeatherModel
) {
    fun toWeatherEntity() = WeatherEntity(
        address = address,
        isCurrent = false,
        latitude = latitude,
        longitude = longitude,
        timeZone = timeZone,
        data = weatherModel,
        lastUpdated = lastUpdated,
    )
}
