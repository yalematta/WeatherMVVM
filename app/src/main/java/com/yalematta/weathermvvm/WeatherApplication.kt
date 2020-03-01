package com.yalematta.weathermvvm

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.yalematta.weathermvvm.data.ApiService
import com.yalematta.weathermvvm.data.db.ForecastDatabase
import com.yalematta.weathermvvm.data.network.ConnectivityInterceptor
import com.yalematta.weathermvvm.data.network.ConnectivityInterceptorImpl
import com.yalematta.weathermvvm.data.network.WeatherNetworkDataSource
import com.yalematta.weathermvvm.data.network.WeatherNetworkDataSourceImpl
import com.yalematta.weathermvvm.data.provider.LocationProvider
import com.yalematta.weathermvvm.data.provider.LocationProviderImpl
import com.yalematta.weathermvvm.data.provider.UnitProvider
import com.yalematta.weathermvvm.data.provider.UnitProviderImpl
import com.yalematta.weathermvvm.data.repository.ForecastRepository
import com.yalematta.weathermvvm.data.repository.ForecastRepositoryImpl
import com.yalematta.weathermvvm.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}