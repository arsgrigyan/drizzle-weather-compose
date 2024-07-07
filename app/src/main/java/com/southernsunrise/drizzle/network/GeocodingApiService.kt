package com.southernsunrise.drizzle.network

import com.southernsunrise.drizzle.BuildConfig
import com.southernsunrise.drizzle.network.models.geocodingModel.GeolocationResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("direct")
    fun searchLocationByName(
        @Query("q") query: String,
        @Query("limit") limit:Int = 5,
        @Query("appid") apiKey:String = BuildConfig.GEOCODING_TEST_API_KEY
    ): Call<GeolocationResponseModel>

    @GET("reverse")
    fun getLocationNameByCoordinates(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("limit") limit:Int = 1,
        @Query("appid") apiKey:String = BuildConfig.GEOCODING_TEST_API_KEY
    ):Call<GeolocationResponseModel>
}