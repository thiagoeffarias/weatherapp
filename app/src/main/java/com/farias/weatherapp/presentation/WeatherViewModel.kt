package com.farias.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farias.weatherapp.data.location.DefaultLocationTracker.Companion.KEY_CITY_NAME
import com.farias.weatherapp.data.location.DefaultLocationTracker.Companion.KEY_COUNTRY_NAME
import com.farias.weatherapp.domain.location.LocationTracker
import com.farias.weatherapp.domain.repository.WeatherRepository
import com.farias.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            locationTracker.getCurrentLocation()?.let { location ->
                val result = repository.getWeatherData(location.latitude, location.longitude)
                when (result) {
                    is Resource.Success -> {
                        val cityName = location.extras?.getString(KEY_CITY_NAME)
                        val countryName = location.extras?.getString(KEY_COUNTRY_NAME)
                        state = state.copy(
                            cityName = cityName,
                            countryName = countryName,
                            isLoading = false,
                            weatherInfo = result.data,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            weatherInfo = null,
                            error = result.message
                        )
                    }
                }
            } ?: run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location." +
                            "Make sure to grant permission and enable gps."
                )
            }
        }
    }
}

