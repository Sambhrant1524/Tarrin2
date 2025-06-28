package com.example.weatherself.data.repository

import com.example.weatherself.data.local.dao.WeatherDao
import com.example.weatherself.data.local.entity.WeatherEntity
import com.example.weatherself.data.remote.api.WeatherApi
import com.example.weatherself.domain.model.WeatherInfo
import com.example.weatherself.domain.repository.WeatherRepositoryInterface
import com.example.weatherself.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
):WeatherRepositoryInterface {
    override suspend fun getCurrentWeather(cityName: String): Flow<NetworkResult<WeatherInfo>> = flow {
        emit(NetworkResult.Loading())

        try {
            val response = api.getCurrentWeather(cityName)

            if (response.isSuccessful && response.body()!=null){

                val weatherResponse=response.body()!!
                val weatherInfo= WeatherInfo(
                    cityName=weatherResponse.name,
                    temperature = weatherResponse.main.temp,
                    description = weatherResponse.weather[0].description,
                    humidity = weatherResponse.main.humidity,
                    pressure = weatherResponse.main.pressure,
                    windSpeed = weatherResponse.wind.speed,
                    icon = weatherResponse.weather[0].icon,
                    country = weatherResponse.sys.country,
                    feelsLike = weatherResponse.main.feelsLike,
                    tempMax = weatherResponse.main.tempMax,
                    tempMin=weatherResponse.main.tempMin
                )
                dao.insertWeather(weatherInfo.toEntity())
                emit(NetworkResult.Success(weatherInfo))
            }else{
                emit(NetworkResult.Error("Unable to fetch data"))
            }

        }catch (e:Exception){
            emit(NetworkResult.Error(e.message?: "Unknown error occurred"))
        }
    }

    override suspend fun getCurrentWeatherByLocation(
        lat: Double,
        lon: Double
    ): Flow<NetworkResult<WeatherInfo>> = flow{
        emit(NetworkResult.Loading())
try {


            val response = api.getCurrentWeatherByCoordinates(lat, lon)
            if (response.isSuccessful && response.body() != null) {
                val weatherResponse = response.body()!!
                val weatherInfo = WeatherInfo(
                    cityName = weatherResponse.name,
                    temperature = weatherResponse.main.temp,
                    description = weatherResponse.weather[0].description,
                    humidity = weatherResponse.main.humidity,
                    pressure = weatherResponse.main.pressure,
                    windSpeed = weatherResponse.wind.speed,
                    icon = weatherResponse.weather[0].icon,
                    country = weatherResponse.sys.country,
                    feelsLike = weatherResponse.main.feelsLike,
                    tempMin = weatherResponse.main.tempMin,
                    tempMax = weatherResponse.main.tempMax

                )
                dao.insertWeather(weatherInfo.toEntity())
                emit(NetworkResult.Success(weatherInfo))
            } else {
                emit(NetworkResult.Error("Error"))
            }
        }catch (e:Exception){
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    override fun getWeatherHistory(): Flow<List<WeatherInfo>> {
        return dao.getAllWeatherData().map { entities ->
            entities.map { it.toWeatherInfo() }
        }
    }

    override suspend fun deleteWeatherHistory(weatherInfo: WeatherInfo) {
         dao.deleteWeather(weatherInfo.toEntity())
    }
}

private fun WeatherInfo.toEntity(): WeatherEntity{

    return WeatherEntity(
    cityName = cityName,
    temperature = temperature,
    description = description,
    humidity = humidity,
    pressure = pressure ,
    windSpeed= windSpeed,
    icon=icon
    )
}


private fun WeatherEntity.toWeatherInfo(): WeatherInfo{
    return WeatherInfo(
        cityName = cityName,
        temperature = temperature,
        description = description,
        humidity = humidity,
        pressure = pressure ,
        windSpeed= windSpeed,
        icon=icon
    )
}