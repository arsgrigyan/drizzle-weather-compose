package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class AtmosphericPropertiesModel(
    val windSpeed: Number,
    val windDirection: Double,
    val windGust: Double,
    val humidity: Number,
    val visibility: Number,
    val pressure: Double,
    val precipitation: Double,
    val precipitationProbability: Double,
    val precipitationType: List<String>?,
    val cloudCover: Double,
    val dew: Double,
    val snow: Double,
    val snowDepth: Double,
    val uvIndex: Double,
    val severeRisk: Double,
)
