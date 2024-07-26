package com.southernsunrise.drizzle.data.remote.models.geocodingModel

data class GeolocationResponseModelItem(
    val country: String,
    val lat: Double,
    val local_names: com.southernsunrise.drizzle.data.remote.models.geocodingModel.LocalNames,
    val lon: Double,
    val name: String,
    val state: String?
)