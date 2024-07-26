package com.southernsunrise.drizzle.data.repository

import com.southernsunrise.drizzle.BuildConfig
import com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel
import com.southernsunrise.drizzle.data.remote.models.responseWeatherModel.FullWeatherDataResponseModel
import retrofit2.Call

interface WeatherRemoteDataSource {

    /// -------- GEOCODING
    fun searchLocationByName(
        query: String,
        limit: Int = 10,
        apiKey: String = BuildConfig.GEOCODING_TEST_API_KEY
    ): Call<GeolocationResponseModel>

    fun getLocationNameByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String = BuildConfig.GEOCODING_TEST_API_KEY
    ): Call<GeolocationResponseModel>

    /// -------- WEATHER
    fun getFullWeatherDataByLocationName(
        locationName: String,
        apiKey: String = BuildConfig.WEATHER_TEST_API_KEY,
        unitGroup: String = "metric",
    ): Call<FullWeatherDataResponseModel>

    fun getFullWeatherDataByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String = BuildConfig.WEATHER_TEST_API_KEY,
        unitGroup: String = "metric",
    ): Call<FullWeatherDataResponseModel>
}