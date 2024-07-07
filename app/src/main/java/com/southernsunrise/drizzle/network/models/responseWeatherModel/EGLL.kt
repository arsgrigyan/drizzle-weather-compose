package com.southernsunrise.drizzle.network.models.responseWeatherModel

data class EGLL(
    val contribution: Double,
    val distance: Double,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val quality: Int,
    val useCount: Int
)