package com.example.weatherself.domain.usecase

import com.example.weatherself.domain.repository.WeatherRepositoryInterface
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepositoryInterface
) {
    suspend operator fun invoke(cityName:String)= repository.getCurrentWeather(cityName)
}