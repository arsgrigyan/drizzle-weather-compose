package com.southernsunrise.drizzle.network.models.representationWeatherModel

data class WeatherModel(
    val currentWeatherDataModel: CurrentWeatherDataModel,
    val fifteenDaysWeatherDataModel: FifteenDaysWeatherDataModel
)
