package com.southernsunrise.drizzle.network

import com.southernsunrise.drizzle.BuildConfig
import com.southernsunrise.drizzle.network.models.responseWeatherModel.FullWeatherDataResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("{location}")
    fun getFullWeatherDataByLocationName(
        @Path("location") location: String,
        @Query("key") apiKey: String = BuildConfig.WEATHER_TEST_API_KEY,
        @Query("unitGroup") unitGroup: String = "metric",
        ): Call<FullWeatherDataResponseModel>

    @GET("{latitude},{longitude}")
    fun getFullWeatherDataByCoord(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("key") apiKey: String = BuildConfig.WEATHER_TEST_API_KEY,
        @Query("unitGroup") unitGroup: String = "metric",
    ): Call<FullWeatherDataResponseModel>

}