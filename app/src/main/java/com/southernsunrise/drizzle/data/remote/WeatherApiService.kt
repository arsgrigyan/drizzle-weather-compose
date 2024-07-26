package com.southernsunrise.drizzle.data.remote

import com.southernsunrise.drizzle.BuildConfig
import com.southernsunrise.drizzle.data.remote.models.responseWeatherModel.FullWeatherDataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("{location}")
    fun getFullWeatherDataByLocationName(
        @Path("location") location: String,
        @Query("key") apiKey: String ,
        @Query("unitGroup") unitGroup: String = "metric",
        ): Call<FullWeatherDataResponseModel>

    @GET("{latitude},{longitude}")
    fun getFullWeatherDataByCoordinates(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("key") apiKey: String,
        @Query("unitGroup") unitGroup: String = "metric",
    ): Call<FullWeatherDataResponseModel>

}