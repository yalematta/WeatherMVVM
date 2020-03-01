package com.yalematta.weathermvvm.data.repository

import androidx.lifecycle.LiveData
import com.yalematta.weathermvvm.data.db.CurrentWeatherDao
import com.yalematta.weathermvvm.data.db.WeatherLocationDao
import com.yalematta.weathermvvm.data.db.entity.CurrentWeatherEntry
import com.yalematta.weathermvvm.data.db.entity.WeatherLocation
import com.yalematta.weathermvvm.data.network.WeatherNetworkDataSource
import com.yalematta.weathermvvm.data.network.response.CurrentWeatherResponse
import com.yalematta.weathermvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ForecastRepositoryImpl(

    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider

) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        initWeatherData()
        return withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getCurrentWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){

            fetchCurrentWeather()
            return
        }


        if(isFetchedCurrentNeeded(lastWeatherLocation.zonedDatetime))
            fetchCurrentWeather()

    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(), "m"
        )
    }

    private fun isFetchedCurrentNeeded(lastFetchTime : ZonedDateTime) : Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}