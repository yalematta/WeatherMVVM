package com.yalematta.weathermvvm.data.network.response

import com.google.gson.annotations.SerializedName
import com.yalematta.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.yalematta.weathermvvm.data.db.entity.Location
import com.yalematta.weathermvvm.data.db.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)