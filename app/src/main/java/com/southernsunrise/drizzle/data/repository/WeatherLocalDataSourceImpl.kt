package com.southernsunrise.drizzle.data.repository

import com.southernsunrise.drizzle.data.local.WeatherDao
import com.southernsunrise.drizzle.data.local.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao) : WeatherLocalDataSource {

    override suspend fun insertWeatherData(weatherEntity: WeatherEntity) =
        weatherDao.insertWeatherData(weatherEntity)

    override fun getLocationsSavedWeatherData(): Flow<List<WeatherEntity>> =
        weatherDao.getLocationsSavedWeatherData()

    override fun getSavedWeatherDataByLocationName(locationName: String): WeatherEntity = weatherDao.getSavedWeatherDataByLocationName(locationName)

    override suspend fun getCurrent(): WeatherEntity? =
        weatherDao.getCurrent()

    override suspend fun update(weatherEntity: WeatherEntity) =
        weatherDao.update(weatherEntity)

    override suspend fun delete(weatherEntity: WeatherEntity) =
        weatherDao.delete(weatherEntity)
}