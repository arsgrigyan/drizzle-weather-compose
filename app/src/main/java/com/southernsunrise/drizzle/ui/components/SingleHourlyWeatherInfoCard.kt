package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily
import com.southernsunrise.drizzle.ui.theme.lightModePrimaryTextColor

@Composable
fun SingleHourlyWeatherCard(
    modifier: Modifier = Modifier,
    iconResource: Int,
    temperature: Number,
    timeString: String,
) {
    Column(
        modifier = modifier
    ) {

        AutoResizeableText(
            modifier = Modifier
                .weight(1f),
            text = "$temperature°",
            defaultFontSize = 16.sp,
            minFontSize = 8.sp,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Medium,
            color = lightModePrimaryTextColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.weight(0.2f))

        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(5.dp),
            imageVector = ImageVector.vectorResource(iconResource),
            contentDescription = "Weather at $timeString",
            tint = Color.White
        )

        AutoResizeableText(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            text = timeString,
            defaultFontSize = 14.sp,
            minFontSize = 6.sp,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Normal,
            color = lightModePrimaryTextColor,
            textAlign = TextAlign.Center
        )
    }
}