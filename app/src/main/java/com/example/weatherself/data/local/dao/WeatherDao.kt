package com.example.weatherself.data.local.dao

import androidx.room.*
import com.example.weatherself.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {


    @Query("SELECT * FROM weather_table ORDER BY timestamp DESC")
    fun getAllWeatherData(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather_table WHERE cityName = :cityName ORDER BY timestamp DESC LIMIT 1")
    suspend fun getWeatherByCity(cityName : String) : WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather_table WHERE timestamp < :cutoffTime")
    suspend fun deleteOldWeatherData(cutoffTime: Long)
}