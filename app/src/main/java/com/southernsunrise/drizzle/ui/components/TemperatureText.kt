package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.southernsunrise.drizzle.ui.theme.blue_primary_dark
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily

@Composable
fun TemperatureText(
    modifier: Modifier = Modifier,
    temperature: String,
//    unit: TemperatureUnit,
    temperatureDefaultFontSize: TextUnit,
    temperatureMinFontSize: TextUnit,
    unitDefaultFontSize: TextUnit,
    fontFamily: FontFamily = gothamFontFamily,
    fontWeight: FontWeight = FontWeight.Medium,
    maxLines: Int = 1,
    unitMinFontSize: TextUnit,
    color: Color = blue_primary_dark,
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        AutoResizeableText(
            modifier = Modifier.weight(1f).background(Color.Cyan),
            text = temperature,
            defaultFontSize = temperatureDefaultFontSize,
            minFontSize = temperatureMinFontSize,
            color = color,
            maxLines = maxLines,
            fontFamily = fontFamily,
            fontWeight = fontWeight
        )
        Box(modifier = Modifier.weight(0.5f).background(Color.LightGray)) {
            AutoResizeableText(
                text = "",
                fontFamily = gothamFontFamily,
                fontWeight = FontWeight.Medium,
                defaultFontSize = unitDefaultFontSize,
                minFontSize = unitMinFontSize,
                color = color
            )
        }
    }

}