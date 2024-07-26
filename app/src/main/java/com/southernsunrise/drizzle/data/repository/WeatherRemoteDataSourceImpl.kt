package com.southernsunrise.drizzle.data.repository

import com.southernsunrise.drizzle.data.remote.GeocodingApiService
import com.southernsunrise.drizzle.data.remote.WeatherApiService
import com.southernsunrise.drizzle.data.remote.models.responseWeatherModel.FullWeatherDataResponseModel
import retrofit2.Call

class WeatherRemoteDataSourceImpl(
    private val geocodingService: GeocodingApiService,
    private val weatherService: WeatherApiService
) : WeatherRemoteDataSource {
    override fun searchLocationByName(
        query: String,
        limit: Int,
        apiKey: String
    ): Call<com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel> {
        return geocodingService.searchLocationByName(
            query = query,
            limit = limit,
            apiKey = apiKey
        )
    }

    override fun getLocationNameByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Call<com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel> {
        return geocodingService.getLocationNameByCoordinates(
            lat = lat,
            lon = lon,
            apiKey = apiKey
        )
    }

    override fun getFullWeatherDataByLocationName(
        locationName: String,
        apiKey: String,
        unitGroup: String
    ): Call<FullWeatherDataResponseModel> {
        return weatherService.getFullWeatherDataByLocationName(
            location = locationName,
            apiKey = apiKey,
            unitGroup = unitGroup
        )
    }

    override fun getFullWeatherDataByCoordinates(
        lat: Double,
        lon: Double,
        apiKey: String,
        unitGroup: String
    ): Call<FullWeatherDataResponseModel> {
        return weatherService.getFullWeatherDataByCoordinates(
            latitude = lat,
            longitude = lon,
            apiKey = apiKey,
            unitGroup = unitGroup
        )
    }
}