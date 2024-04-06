package com.farias.weatherapp.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.farias.weatherapp.R
import com.farias.weatherapp.domain.weather.WeatherData
import com.farias.weatherapp.presentation.ui.theme.DarkCardBackground
import com.farias.weatherapp.presentation.ui.theme.LightCardBackground
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
internal fun WeatherHourlyCard(weather: WeatherData) {
    val cardBackground = if (isSystemInDarkTheme()) {
        DarkCardBackground
    } else {
        LightCardBackground
    }
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(weather.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weather.temperatureCelsius} ºC",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_drop),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weather.humidity.roundToInt()}%",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_wind),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = Color.Cyan
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${weather.windSpeed.roundToInt()}km/h",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = weather.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                color = Color.White,
            )
        }
    }
}