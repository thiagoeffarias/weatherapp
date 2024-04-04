package com.farias.weatherapp.data.repository

import com.farias.weatherapp.data.mappers.toWeatherInfo
import com.farias.weatherapp.data.remote.WeatherApi
import com.farias.weatherapp.domain.repository.WeatherRepository
import com.farias.weatherapp.domain.util.Resource
import com.farias.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Resource<WeatherInfo> {
        return try {
            val data = api.getWeatherData(latitude, longitude).toWeatherInfo()
            Resource.Success(data = data)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message)
        }
    }
}
