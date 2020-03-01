package com.yalematta.weathermvvm

import android.app.Application
import com.yalematta.weathermvvm.data.ApiService
import com.yalematta.weathermvvm.data.db.ForecastDatabase
import com.yalematta.weathermvvm.data.network.ConnectivityInterceptor
import com.yalematta.weathermvvm.data.network.ConnectivityInterceptorImpl
import com.yalematta.weathermvvm.data.network.WeatherNetworkDataSource
import com.yalematta.weathermvvm.data.network.WeatherNetworkDataSourceImpl
import com.yalematta.weathermvvm.data.repository.ForecastRepository
import com.yalematta.weathermvvm.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }
}