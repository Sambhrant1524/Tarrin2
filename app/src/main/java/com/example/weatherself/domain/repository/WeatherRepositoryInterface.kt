package com.example.weatherself.domain.repository

import com.example.weatherself.domain.model.WeatherInfo
import com.example.weatherself.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    suspend fun getCurrentWeather(cityName:String): Flow<NetworkResult<WeatherInfo>>
    suspend fun getCurrentWeatherByLocation(lat: Double, lon: Double): Flow<NetworkResult<WeatherInfo>>
    fun getWeatherHistory(): Flow<List<WeatherInfo>>
    suspend fun deleteWeatherHistory(weatherInfo: WeatherInfo)
}