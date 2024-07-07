package com.southernsunrise.drizzle.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val verticalGradientClearSkyDay =
    Brush.verticalGradient(colors = listOf(Color(0xFF4D8CDE), Color(0xFF6A9DE1)))
val verticalGradientClearSkyNight =
    Brush.verticalGradient(colors = listOf(Color(0xFF2B387E), Color(0xFF955fa1)))

val verticalGradientCloudyDay =
    Brush.verticalGradient(colors = listOf(Color(0xFF5594b3), Color(0xFF5594b3)))
val verticalGradientCloudyNight =
    Brush.verticalGradient(colors = listOf(Color(0xFF32637C), Color(0xFF3F6D83)))


val verticalGradientFog =
    Brush.verticalGradient(colors = listOf(Color(0xFFB28DBB), Color(0xFF686195)))

val verticalGradientRain =
    Brush.verticalGradient(colors = listOf(Color(0xFF5a6fa3), Color(0xFFC3A2AE)))

val verticalGradientWindSnowSleetHail =
    Brush.verticalGradient(colors = listOf(Color(0xFF7b7d88), Color(0xFFaab5ba)))

val verticalGradientThunderstorm = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF333333), // Dark Gray (rain clouds)
        Color(0xFF4b6b88)  // Electric Blue (lightning)
    )
)
val verticalGradientSandstorm =
    Brush.verticalGradient(colors = listOf(Color(0xffb3733b), Color(0xFFd39c59)))
val verticalGradientTornado =
    Brush.verticalGradient(colors = listOf(Color(0xff474042), Color(0xFFaa8171)))


val listOverlayHorizontalGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0f to Color.White.copy(alpha = 0.1f),
        0.075f to Color.Transparent,
        0.925f to Color.Transparent,
        1f to Color.White.copy(alpha = 0.1f)
    )
)