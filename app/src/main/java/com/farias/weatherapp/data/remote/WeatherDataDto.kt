package com.farias.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto(
    val time: List<String>,
    @Json(name = "temperature_2m")
    val temperature: List<Double>,
    @Json(name = "weathercode")
    val weatherCodes: List<Int>,
    @Json(name = "pressure_msl")
    val pressure: List<Double>,
    @Json(name = "windspeed_10m")
    val windSpeed: List<Double>,
    @Json(name = "relativehumidity_2m")
    val relativeHumidity: List<Double>,
)
