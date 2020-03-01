package com.yalematta.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.yalematta.weathermvvm.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
}