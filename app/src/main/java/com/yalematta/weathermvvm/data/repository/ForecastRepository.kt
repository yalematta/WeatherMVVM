package com.yalematta.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.yalematta.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.yalematta.weathermvvm.data.db.entity.WeatherLocation

interface ForecastRepository {

    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation():LiveData<WeatherLocation>
}