package com.yalematta.weathermvvm.data.network

import androidx.lifecycle.LiveData
import com.yalematta.weathermvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )
}