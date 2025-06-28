package com.example.weatherself.domain.model

data class WeatherInfo(

    val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val icon: String,
    val country: String = "",
    val feelsLike: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0
)
