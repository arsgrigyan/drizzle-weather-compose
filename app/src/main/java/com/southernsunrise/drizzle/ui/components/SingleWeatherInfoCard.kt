package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily
import com.southernsunrise.drizzle.ui.theme.lightModePrimaryTextColor

@Composable
fun SingleWeatherInfoCardVertical(
    modifier: Modifier = Modifier,
    iconResource: Int,
    title: String,
    timeString: String,
) {
    Column(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            imageVector = ImageVector.vectorResource(iconResource),
            contentDescription = title,
            tint = Color.White
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f),
            contentAlignment = Alignment.Center
        ) {
            AutoResizeableText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title,
                defaultFontSize = 16.sp,
                minFontSize = 8.sp,
                fontFamily = gothamFontFamily,
                fontWeight = FontWeight.Medium,
                color = lightModePrimaryTextColor,
                textAlign = TextAlign.Center
            )
        }

        AutoResizeableText(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = timeString,
            defaultFontSize = 16.sp,
            minFontSize = 8.sp,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Normal,
            color = lightModePrimaryTextColor,
            textAlign = TextAlign.Center
        )

    }
}