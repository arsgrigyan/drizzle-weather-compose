package com.southernsunrise.drizzle.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.WeatherModel

@Entity(tableName = "resents_forecast_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("address")
    val address: String,

    @ColumnInfo("is_current")
    var isCurrent: Boolean,

    @ColumnInfo("latitude")
    val latitude: Double,

    @ColumnInfo("longitude")
    val longitude: Double,

    @ColumnInfo("time_zone")
    val timeZone: String,

    @ColumnInfo("forecast_info")
    val data: WeatherModel,

    @ColumnInfo("last_updated")
    val lastUpdated: Long,

    ) {
    fun toLocationFullWeatherModel() =
        LocationFullWeatherModel(
            address = address,
            latitude = latitude,
            longitude = longitude,
            timeZone = timeZone,
            weatherModel = data,
            lastUpdated = lastUpdated,
        )
}
