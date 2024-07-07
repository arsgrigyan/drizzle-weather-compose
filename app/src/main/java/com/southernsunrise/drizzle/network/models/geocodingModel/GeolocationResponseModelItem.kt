package com.southernsunrise.drizzle.network.models.geocodingModel

data class GeolocationResponseModelItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String?
)