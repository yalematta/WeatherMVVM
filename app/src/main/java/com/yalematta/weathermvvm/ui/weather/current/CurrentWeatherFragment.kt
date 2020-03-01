package com.yalematta.weathermvvm.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yalematta.weathermvvm.R
import com.yalematta.weathermvvm.internal.glide.GlideApp
import com.yalematta.weathermvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {

            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("London")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("${it.weatherIcons[0]}")
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location : String){
        (activity as AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double){
        textView_temperature.text = "$temperature"
        textView_feels_like_temperature.text = "Feels like $temperature"
    }

    private fun updateCondition(condition: String){
        textView_condition.text = "$condition"
    }

    private fun updatePrecipitation(precipitation: Double){
        textView_precipitation.text = "Precipitation: $precipitation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind.text = "Wind: $windDirection, $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility.text = "Visibility: $visibilityDistance"
    }

}
