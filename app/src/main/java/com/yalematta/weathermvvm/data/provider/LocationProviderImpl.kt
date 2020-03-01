package com.yalematta.weathermvvm.data.provider

import com.yalematta.weathermvvm.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
       return "Los Angeles"
    }
}