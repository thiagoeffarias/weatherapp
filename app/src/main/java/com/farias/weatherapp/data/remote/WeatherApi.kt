package com.farias.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ) : WeatherDto


    companion object {
        private const val API_VERSION = "v1"
        const val BASE_URL = "https://api.open-meteo.com/$API_VERSION/"
    }
}