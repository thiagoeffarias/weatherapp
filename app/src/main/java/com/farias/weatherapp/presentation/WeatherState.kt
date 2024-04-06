package com.farias.weatherapp.presentation

import com.farias.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val cityName: String? = null,
    val countryName: String? = null,
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
