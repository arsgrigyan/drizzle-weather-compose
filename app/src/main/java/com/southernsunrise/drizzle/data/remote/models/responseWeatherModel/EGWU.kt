package com.southernsunrise.drizzle.data.remote.models.responseWeatherModel

data class EGWU(
    val contribution: Double,
    val distance: Double,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val quality: Int,
    val useCount: Int
)