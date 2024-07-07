package com.southernsunrise.drizzle.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL_WEATHER = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
    //private const val BASE_URL_GEOCODING = "https://nominatim.openstreetmap.org/"
    private const val BASE_URL_GEOCODING = "https://api.openweathermap.org/geo/1.0/"

    private val retrofitGeocoding: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_GEOCODING)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val geocodingApiService: GeocodingApiService by lazy {
        retrofitGeocoding.create(GeocodingApiService::class.java)
    }

    private val retrofitWeather: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApiService: WeatherApiService by lazy {
        retrofitWeather.create(WeatherApiService::class.java)
    }
}