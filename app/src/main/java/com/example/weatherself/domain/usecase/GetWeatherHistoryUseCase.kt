package com.example.weatherself.domain.usecase

import com.example.weatherself.domain.repository.WeatherRepositoryInterface
import javax.inject.Inject

class GetWeatherHistoryUseCase@Inject constructor(
    private val repository: WeatherRepositoryInterface
) {
    operator fun invoke()=repository.getWeatherHistory()
}