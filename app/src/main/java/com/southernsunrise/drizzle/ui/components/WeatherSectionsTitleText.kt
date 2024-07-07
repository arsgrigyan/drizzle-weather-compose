package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily
import com.southernsunrise.drizzle.ui.theme.lightModePrimaryTextColor
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusSmall

@Composable
fun WeatherSectionsTitleText(modifier: Modifier = Modifier, text:String, backgroundColor: Color) {
    AutoResizeableText(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 10.dp,
                    bottomStart = weatherInfoCardsCornerRadiusSmall,
                    bottomEnd = 10.dp
                )
            )
            .background(backgroundColor)
            .padding(horizontal = 15.dp, vertical = 5.dp),
        text = text,
        textAlign = TextAlign.Start,
        defaultFontSize = 20.sp,
        minFontSize = 12.sp,
        color = lightModePrimaryTextColor,
        fontFamily = gothamFontFamily,
        fontWeight = FontWeight.Medium,
    )
}