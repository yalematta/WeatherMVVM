package com.yalematta.weathermvvm.data.network.response

import com.google.gson.annotations.SerializedName
import com.yalematta.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.yalematta.weathermvvm.data.db.entity.Request
import com.yalematta.weathermvvm.data.db.entity.WeatherLocation


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)