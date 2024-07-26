package com.southernsunrise.drizzle.application

import android.content.Context
import com.southernsunrise.drizzle.data.local.AppDatabase
import com.southernsunrise.drizzle.data.remote.GeocodingApiService
import com.southernsunrise.drizzle.data.remote.WeatherApiService
import com.southernsunrise.drizzle.data.repository.WeatherLocalDataSource
import com.southernsunrise.drizzle.data.repository.WeatherLocalDataSourceImpl
import com.southernsunrise.drizzle.data.repository.WeatherRemoteDataSourceImpl
import com.southernsunrise.drizzle.data.repository.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val geocodingBaseUrl = "https://api.openweathermap.org/geo/1.0/"
    private val weatherBaseUrl =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"

    private val retrofitGeocoding: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(geocodingBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitWeather: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val geocodingApiService: GeocodingApiService by lazy {
        retrofitGeocoding.create(GeocodingApiService::class.java)
    }

    private val weatherApiService: WeatherApiService by lazy {
        retrofitWeather.create(WeatherApiService::class.java)
    }

    private val localDataSource = WeatherLocalDataSourceImpl(AppDatabase.getInstance(context).getWeatherDao())
    private val remoteDataSource = WeatherRemoteDataSourceImpl(geocodingApiService, weatherApiService)

    val weatherRepository = WeatherRepository(localDataSource, remoteDataSource)

}