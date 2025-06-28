package com.example.weatherself.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.query
import com.example.weatherself.domain.model.WeatherInfo
import com.example.weatherself.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherself.domain.usecase.GetWeatherHistoryUseCase
import com.example.weatherself.utils.Constants
import com.example.weatherself.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
):ViewModel() {

    private val _weatherState = MutableStateFlow<NetworkResult<WeatherInfo>>(NetworkResult.Loading())
     val weatherState: StateFlow<NetworkResult<WeatherInfo>> = _weatherState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery:StateFlow<String> = _searchQuery.asStateFlow()

    private val _weatherHistory  = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherHistory: StateFlow<List<WeatherInfo>> = _weatherHistory.asStateFlow()

    init{
        setupDebouncedSearch()
        loadWeatherHistory()
        searchWeather("London")
    }

    private fun setupDebouncedSearch(){
        viewModelScope.launch {
            _searchQuery
                .debounce(Constants.debounce_delay)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collect{ query ->
                    searchWeather(query)

                }
        }
    }

    fun updateSearchQuery(query: String){
        _searchQuery.value=query
    }

     fun searchWeather(cityName: String){
        viewModelScope.launch {
            getCurrentWeatherUseCase(cityName).collect{result ->
                _weatherState.value=result
            }
        }
    }

    private fun loadWeatherHistory(){
        viewModelScope.launch {
            getWeatherHistoryUseCase().collect{result ->
                _weatherHistory.value=result

            }
        }
    }
    fun refreshWeather(){
        val currentQuery = _searchQuery.value.ifBlank{"London"}
        searchWeather(currentQuery)
    }



}