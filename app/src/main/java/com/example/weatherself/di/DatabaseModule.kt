package com.example.weatherself.di

import android.content.Context
import androidx.room.Room
import com.example.weatherself.data.local.dao.WeatherDao
import com.example.weatherself.data.local.database.WeatherDatabase
import com.example.weatherself.data.repository.WeatherRepository
import com.example.weatherself.domain.repository.WeatherRepositoryInterface
import com.example.weatherself.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase{
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase):WeatherDao{
        return database.weatherDao()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepository: WeatherRepository
    ): WeatherRepositoryInterface
}