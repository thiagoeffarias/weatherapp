package com.farias.weatherapp.domain.repository

import com.farias.weatherapp.domain.util.Resource
import com.farias.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(latitude: Double, longitude: Double): Resource<WeatherInfo>
}