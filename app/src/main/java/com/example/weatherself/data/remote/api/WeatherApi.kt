package com.example.weatherself.data.remote.api

import com.example.weatherself.data.remote.dto.WeatherResponse
import com.example.weatherself.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(

        @Query("q") cityName : String,
        @Query("appid") apiKey: String = Constants.API_Key,
        @Query("units") units: String= "metric"
    ) : Response<WeatherResponse>

    @GET("weather")
    suspend fun getCurrentWeatherByCoordinates(

        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Constants.API_Key,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}