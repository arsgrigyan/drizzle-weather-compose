package com.southernsunrise.drizzle.data.repository

import com.southernsunrise.drizzle.data.local.WeatherEntity
import com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel
import com.southernsunrise.drizzle.data.remote.models.responseWeatherModel.FullWeatherDataResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class WeatherRepository(
    private val weatherLocalDataSource: WeatherLocalDataSourceImpl,
    private val weatherRemoteDataSource: WeatherRemoteDataSourceImpl
) : WeatherLocalDataSource, WeatherRemoteDataSource {

    override suspend fun insertWeatherData(weatherEntity: WeatherEntity) {
        weatherLocalDataSource.insertWeatherData(weatherEntity)
    }

    override fun getLocationsSavedWeatherData(): Flow<List<WeatherEntity>> {
        return weatherLocalDataSource.getLocationsSavedWeatherData()
    }

    override fun getSavedWeatherDataByLocationName(locationName: String): WeatherEntity {
        return weatherLocalDataSource.getSavedWeatherDataByLocationName(locationName)
    }

    override suspend fun getCurrent(): WeatherEntity? {
        return weatherLocalDataSource.getCurrent()
    }

    override suspend fun update(weatherEntity: WeatherEntity) {
        weatherLocalDataSource.update(weatherEntity)
    }

    override suspend fun delete(weatherEntity: WeatherEntity) {
        weatherLocalDataSource.delete(weatherEntity)
    }

    override fun searchLocationByName(
        query: String,
        limit: Int,
        apiKey: String
    ): Call<GeolocationResponseModel> {
        return weatherRemoteDataSource.searchLocationByName(query, limit, apiKey)
    }

    override fun getLocationNameByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Call<GeolocationResponseModel> {
        return weatherRemoteDataSource.getLocationNameByCoordinates(lat, lon, apiKey)
    }

    override fun getFullWeatherDataByLocationName(
        locationName: String,
        apiKey: String,
        unitGroup: String
    ): Call<FullWeatherDataResponseModel> {
        return weatherRemoteDataSource.getFullWeatherDataByLocationName(
            locationName,
            apiKey,
            unitGroup
        )
    }

    override fun getFullWeatherDataByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String,
        unitGroup: String
    ): Call<FullWeatherDataResponseModel> {
        return weatherRemoteDataSource.getFullWeatherDataByCoordinates(lat, lon, apiKey, unitGroup)
    }

}