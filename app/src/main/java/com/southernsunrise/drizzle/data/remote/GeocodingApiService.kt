package com.southernsunrise.drizzle.data.remote

import com.southernsunrise.drizzle.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("direct")
    fun searchLocationByName(
        @Query("q") query: String,
        @Query("limit") limit:Int,
        @Query("appid") apiKey:String = BuildConfig.GEOCODING_TEST_API_KEY
    ): Call<com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel>

    @GET("reverse")
    fun getLocationNameByCoordinates(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("limit") limit:Int = 1,
        @Query("appid") apiKey:String = BuildConfig.GEOCODING_TEST_API_KEY
    ):Call<com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel>
}