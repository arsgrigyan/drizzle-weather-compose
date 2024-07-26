package com.southernsunrise.drizzle.data.repository

import com.southernsunrise.drizzle.data.local.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface WeatherLocalDataSource {
    suspend fun insertWeatherData(weatherEntity: WeatherEntity)

    fun getLocationsSavedWeatherData(): Flow<List<WeatherEntity>>

    fun getSavedWeatherDataByLocationName(locationName:String): WeatherEntity

    suspend fun getCurrent(): WeatherEntity?

    suspend fun update(weatherEntity: WeatherEntity)

    suspend fun delete(weatherEntity: WeatherEntity)
}