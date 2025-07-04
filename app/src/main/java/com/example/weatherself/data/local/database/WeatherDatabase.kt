package com.example.weatherself.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherself.data.local.dao.WeatherDao
import com.example.weatherself.data.local.entity.WeatherEntity


@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema= false
)

abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}