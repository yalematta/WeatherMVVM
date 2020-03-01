package com.yalematta.weathermvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.yalematta.weathermvvm.data.repository.ForecastRepository
import com.yalematta.weathermvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }


}
