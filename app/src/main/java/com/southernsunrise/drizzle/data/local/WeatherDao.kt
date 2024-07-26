package com.southernsunrise.drizzle.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM resents_forecast_table")
    fun getLocationsSavedWeatherData(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM resents_forecast_table WHERE address = :locationName")
    fun getSavedWeatherDataByLocationName(locationName:String): WeatherEntity

    @Query("SELECT * FROM resents_forecast_table WHERE is_current= 1")
    suspend fun getCurrent(): WeatherEntity?

    @Update
    suspend fun update(weatherEntity: WeatherEntity)

    @Delete
    suspend fun delete(weatherEntity: WeatherEntity)
}

