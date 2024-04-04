package com.farias.weatherapp.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.farias.weatherapp.data.remote.WeatherDataDto
import com.farias.weatherapp.data.remote.WeatherDto
import com.farias.weatherapp.domain.weather.WeatherData
import com.farias.weatherapp.domain.weather.WeatherInfo
import com.farias.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

internal fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature[index],
                pressure = pressure[index],
                windSpeed = windSpeed[index],
                humidity = relativeHumidity[index],
                weatherType = WeatherType.fromWMO(weatherCodes[index]),
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map {
            it.data
        }
    }
}

internal fun WeatherDto.toWeatherInfo() : WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
     val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
    )
}