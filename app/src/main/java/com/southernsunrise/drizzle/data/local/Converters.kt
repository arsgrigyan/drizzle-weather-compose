package com.southernsunrise.drizzle.data.local
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.WeatherModel

class Converters {
    @TypeConverter
    fun fromWeatherModel(value: WeatherModel?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWeatherModel(value: String): WeatherModel {
        val gson = Gson()
        val type = object : TypeToken<WeatherModel>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromWeatherModelList(value: List<WeatherModel>?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWeatherModelList(value: String): List<WeatherModel> {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherModel>>() {}.type
        return gson.fromJson(value, type)
    }
}