package com.farias.weatherapp.presentation.components

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farias.weatherapp.domain.weather.WeatherData
import com.farias.weatherapp.presentation.ui.theme.DarkCardBackground
import com.farias.weatherapp.presentation.ui.theme.LightCardBackground

@Composable
internal fun WeatherWeekDayCard(
    weather: Pair<WeatherData, WeatherData>,
    modifier: Modifier = Modifier
) {
    val cardBackground = if (isSystemInDarkTheme()) {
        DarkCardBackground
    } else {
        LightCardBackground
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        modifier = modifier.wrapContentSize(),
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .aspectRatio(1f)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = "${weather.first.time.dayOfWeek}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (modifier = Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Column(
                    modifier = Modifier.wrapContentWidth().height(80.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Max weather
                    Image(
                        painter = painterResource(id = weather.first.weatherType.iconRes),
                        contentDescription = weather.first.weatherType.weatherDesc,
                        modifier = Modifier
                            .width(20.dp)
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Max:\n${weather.first.temperatureCelsius}ºC",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                // Min weather
                Column(
                    modifier = Modifier.wrapContentWidth().height(80.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = weather.second.weatherType.iconRes),
                        contentDescription = weather.second.weatherType.weatherDesc,
                        modifier = Modifier
                            .width(20.dp)
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Min:\n${weather.second.temperatureCelsius}ºC",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}