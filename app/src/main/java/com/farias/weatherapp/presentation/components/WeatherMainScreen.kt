package com.farias.weatherapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farias.weatherapp.data.mappers.toWeatherDataMap
import com.farias.weatherapp.data.remote.WeatherDataDto
import com.farias.weatherapp.domain.weather.WeatherData
import com.farias.weatherapp.domain.weather.WeatherInfo
import com.farias.weatherapp.presentation.WeatherState
import com.farias.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime

@Composable
fun WeatherMainScreen(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        if (state.isLoading) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Color.White,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 8.dp,
                )
            }
        } else {
            MainScreen(state, modifier)
        }
    }
}

@Composable
internal fun MainScreen(state: WeatherState, modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .wrapContentHeight()
            .verticalScroll(state = scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        WeatherCard(state, modifier)
        Spacer(modifier = Modifier.height(16.dp))
        WeekSection(state)
        Spacer(modifier = Modifier.height(16.dp))
        HourSection(state)
    }
}

@Composable
internal fun WeekSection(state: WeatherState) {
    state.weatherInfo?.let {
        val sevenDayForecast = mutableListOf<Pair<WeatherData, WeatherData>>()
        for (index in 0..state.weatherInfo.weatherDataPerDay.size) {
            state.weatherInfo.weatherDataPerDay[index]?.let { dayWeather ->
                val maxWeather = dayWeather.maxBy { it.temperatureCelsius }
                val minWeather = dayWeather.minBy { it.temperatureCelsius }
                sevenDayForecast.add(Pair(maxWeather, minWeather))
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(sevenDayForecast) { weather ->
                WeatherWeekDayCard(weather)
            }
        }
    }
}

@Composable
internal fun HourSection(state: WeatherState) {
    state.weatherInfo?.let {
        state.weatherInfo.weatherDataPerDay[0]?.let { hourly ->
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                hourly.forEach { weather ->
                    WeatherHourlyCard(weather)
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewWeatherCard() {
    WeatherAppTheme {
        WeatherMainScreen(
            state = WeatherState(
                cityName = "Amsterdam",
                countryName = "Netherland",
                weatherInfo = data,
                isLoading = false
            ),
        )
    }
}


private val today = LocalDateTime.now()
private val weatherData = WeatherDataDto(
    time = listOf(
        today.toString(),
        today.plusDays(1).toString(),
        today.plusDays(2).toString(),
    ),
    temperature = listOf(30.0, 40.0, 50.0, 60.0),
    pressure = listOf(10.0, 20.0, 30.0, 40.0),
    windSpeed = listOf(6.5, 6.3, 8.0, 9.0),
    relativeHumidity = listOf(60.0, 55.0, 78.0, 94.0),
    weatherCodes = listOf(0, 2, 4, 8)
).toWeatherDataMap()
private val data = WeatherInfo(
    currentWeatherData = weatherData.entries.first().value.first(),
    weatherDataPerDay = weatherData,
)