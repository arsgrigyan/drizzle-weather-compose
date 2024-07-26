package com.southernsunrise.drizzle.data.remote.models.representationWeatherModel

data class WeatherModel(
    val currentWeatherDataModel: CurrentWeatherDataModel,
    val fifteenDaysWeatherDataModel: FifteenDaysWeatherDataModel
)
