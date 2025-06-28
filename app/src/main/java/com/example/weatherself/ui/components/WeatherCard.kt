package com.example.weatherself.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherself.domain.model.WeatherInfo
import kotlin.math.roundToInt


@Composable
fun WeatherCard(
    weatherInfo: WeatherInfo,
    modifier: Modifier=Modifier
){
    val backgroundGradient = getWeatherGradient(weatherInfo.description)


    Card(
        modifier=Modifier
            .fillMaxWidth()


            .padding(16.dp ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF00C6FF) // ← Replace with your desired color
        )


    ){
        Column(
            modifier=Modifier
                .padding(16.dp)






        ) {
            Text(
                text = "${weatherInfo.cityName}, ${weatherInfo.country}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier=Modifier.height(8.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "${weatherInfo.temperature.roundToInt()}",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = weatherInfo.description.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Text(
                        text = "Feels like ${weatherInfo.feelsLike.roundToInt()}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

            }
            Spacer(modifier=Modifier.height(24.dp))
            // Weather Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(
                    icon = Icons.Default.Home,
                    label = "Humidity",
                    value = "${weatherInfo.humidity}%"
                )
                WeatherDetailItem(
                    icon = Icons.Default.Warning,
                    label = "Wind",
                    value = "${weatherInfo.windSpeed} m/s"
                )
                WeatherDetailItem(
                    icon = Icons.Default.Place,
                    label = "Pressure",
                    value = "${weatherInfo.pressure} hPa"
                )
            }

        }
    }
}
@Composable
fun WeatherDetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}
private fun getWeatherGradient(description: String): Brush {
    return when {
        description.contains("clear", ignoreCase = true) ||
                description.contains("sunny", ignoreCase = true) -> {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFF7043),
                    Color(0xFFFFB74D)
                )
            )
        }
        description.contains("cloud", ignoreCase = true) -> {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFF607D8B),
                    Color(0xFF90A4AE)
                )
            )
        }
        description.contains("rain", ignoreCase = true) -> {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFF1976D2),
                    Color(0xFF42A5F5)
                )
            )
        }
        description.contains("snow", ignoreCase = true) -> {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFF90CAF9),
                    Color(0xFFE3F2FD)
                )
            )
        }
        else -> {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFF5E35B1),
                    Color(0xFF7E57C2)
                )
            )
        }
    }
}