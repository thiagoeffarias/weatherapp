package com.farias.weatherapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farias.weatherapp.R
import com.farias.weatherapp.data.mappers.toWeatherDataMap
import com.farias.weatherapp.data.remote.WeatherDataDto
import com.farias.weatherapp.domain.weather.WeatherInfo
import com.farias.weatherapp.presentation.WeatherState
import com.farias.weatherapp.presentation.ui.theme.DarkCardBackground
import com.farias.weatherapp.presentation.ui.theme.LightCardBackground
import com.farias.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val cardBackground = if (isSystemInDarkTheme()) {
        DarkCardBackground
    } else {
        LightCardBackground
    }

    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .background(Color.Transparent),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${state.cityName} - ${state.countryName}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        softWrap = true,
                        modifier = Modifier
                            .weight(2f)
                            .wrapContentHeight()
                    )

                    Text(
                        text = "Today ${data.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = data.weatherType.weatherDesc,
                    modifier = Modifier
                        .width(180.dp)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${data.temperatureCelsius}ºC",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(R.drawable.ic_pressure),
                        iconTint = MaterialTheme.colorScheme.onBackground,
                    )
                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = "%",
                        icon = ImageVector.vectorResource(R.drawable.ic_drop)
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h",
                        icon = ImageVector.vectorResource(R.drawable.ic_wind)
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onBackground
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewWeatherCard() {
    WeatherAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            WeatherCard(
                state = WeatherState(
                    cityName = "Amsterdam",
                    countryName = "Netherland",
                    weatherInfo = data
                ),
            )
        }
    }
}


private val today = LocalDateTime.now()
private val weatherData = WeatherDataDto(
    time = listOf(
        today.toString(),
        today.plusDays(1).toString(),
        today.plusDays(1).toString(),
    ),
    temperature = listOf(30.0, 40.0, 50.0),
    pressure = listOf(10.0, 20.0, 30.0),
    windSpeed = listOf(6.5, 6.3, 8.0),
    relativeHumidity = listOf(60.0, 55.0, 78.0),
    weatherCodes = listOf(0, 2, 4)
).toWeatherDataMap()
private val data = WeatherInfo(
    currentWeatherData = weatherData.entries.first().value.first(),
    weatherDataPerDay = weatherData,
)