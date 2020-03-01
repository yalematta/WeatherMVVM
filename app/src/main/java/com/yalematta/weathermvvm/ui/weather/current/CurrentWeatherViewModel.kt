package com.yalematta.weathermvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.yalematta.weathermvvm.data.provider.UnitProvider
import com.yalematta.weathermvvm.data.repository.ForecastRepository
import com.yalematta.weathermvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }

}
