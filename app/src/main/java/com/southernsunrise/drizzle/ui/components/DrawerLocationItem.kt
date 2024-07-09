package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.network.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily

@Composable
fun DrawerLocationItem(
    modifier: Modifier = Modifier,
    locationFullWeatherModel: LocationFullWeatherModel,
    textFontSize: TextUnit = 26.sp,
    textColor: Color,
    iconTint: Color,
    backgroundTint: Color,
    backgroundCornerRadius: Dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = backgroundCornerRadius))
            .background(backgroundTint)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .then(modifier),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = locationFullWeatherModel.resolvedAddress,
                fontFamily = gothamFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = textFontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = textColor
            )
            Spacer(modifier = Modifier.weight(0.2f))
            Row(
                modifier = Modifier.weight(1.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    painter = painterResource(id = locationFullWeatherModel.weatherModel.currentWeatherDataModel.weatherMedia.weatherIcon),
                    contentDescription = null,
                    tint = iconTint
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = locationFullWeatherModel.weatherModel.currentWeatherDataModel.temperaturePropertiesModel.tempAverage.toString() + "°",
                        fontFamily = gothamFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = (textFontSize.value - 3).sp,
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        color = textColor
                    )
                }

            }
        }
    }
}

