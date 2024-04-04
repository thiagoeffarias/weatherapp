package com.farias.weatherapp.data.remote

import com.squareup.moshi.Json
import javax.inject.Named

data class WeatherDto(
    @Json(name = "hourly")
    val weatherData: WeatherDataDto,
)