package com.farias.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherUnitsDto(
    @field:Json(name = "temperature_2m")
    val temperatureUnit: String,
    @field:Json(name = "relative_humidity_2m")
    val relativeHumidityUnit: String,
    @field:Json(name = "wind_speed_10m")
    val windSpeedUnit: String,
    @field:Json(name = "pressure_msl")
    val pressureUnit: String,
)