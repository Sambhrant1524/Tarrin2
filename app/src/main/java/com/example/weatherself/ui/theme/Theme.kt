package com.example.weatherself.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Your HTML design color palette
private val Aqua = Color(0xFF04C7D0)
private val BlueGray = Color(0xFF4B708E)
private val SoftGreen = Color(0xFF28C481)
private val GoldYellow = Color(0xFFFFC233)
private val ErrorRed = Color(0xFFF55A3D)
private val LightBackground = Color(0xFFF8F8F9)
private val WhiteSurface = Color(0xFFFFFFFF)
private val OnDark = Color.White
private val OnLight = Color(0xFF141416) // Almost black

private val LightColorScheme = lightColorScheme(
    primary = Aqua,
    onPrimary = OnDark,
    secondary = BlueGray,
    onSecondary = OnDark,
    tertiary = GoldYellow,
    onTertiary = OnDark,
    background = LightBackground,
    onBackground = OnLight,
    surface = WhiteSurface,
    onSurface = OnLight,
    error = ErrorRed,
    onError = OnDark
)

private val DarkColorScheme = darkColorScheme(
    primary = Aqua,
    onPrimary = Color.Black,
    secondary = BlueGray,
    onSecondary = Color.White,
    tertiary = GoldYellow,
    onTertiary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun WeatherSelfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
