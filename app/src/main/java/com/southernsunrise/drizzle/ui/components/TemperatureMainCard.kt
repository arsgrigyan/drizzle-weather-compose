package com.southernsunrise.drizzle.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.network.models.representationWeatherModel.CurrentWeatherDataModel
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily
import com.southernsunrise.drizzle.ui.theme.lightModePrimaryTextColor
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardBackgroundColorLight
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.utils.updatedAgo


@Composable
fun TemperatureMainCard(
    modifier: Modifier = Modifier,
    currentWeatherData: CurrentWeatherDataModel,
    cardBackgroundColor: Color = weatherInfoCardBackgroundColorLight,
    cardCornerRadius: Dp = weatherInfoCardsCornerRadiusDefault
) {

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(cardCornerRadius))
            .background(cardBackgroundColor)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        DateText(
            modifier = Modifier

                .weight(1f)
                .fillMaxWidth(0.6f),
            dateText = updatedAgo(currentWeatherData.datetimeEpoch)
        )
        WeatherIconAndDegrees(
            modifier = Modifier
                .weight(3 / 2f)
                .fillMaxWidth(0.7f),
            weatherData = currentWeatherData
        )
        MaxMinFeelsLikeAndWeatherDescription(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.7f),
            weatherData = currentWeatherData
        )

    }
}

@SuppressLint("NewApi")
@Composable
private fun DateText(modifier: Modifier = Modifier, dateText: String) {

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AutoResizeableText(
                modifier = Modifier
                    .weight(1.5f),
                textAlign = TextAlign.Center,
                text = "Today",
                defaultFontSize = 30.sp,
                fontFamily = gothamFontFamily,
                fontWeight = FontWeight.Medium,
                color = lightModePrimaryTextColor,
                minFontSize = 18.sp
            )

            AutoResizeableText(
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontFamily = gothamFontFamily,
                fontWeight = FontWeight.Normal,
                color = lightModePrimaryTextColor,
                text = dateText,
                defaultFontSize = 14.sp, minFontSize = 12.sp
            )
        }
    }

}

@Composable
private fun WeatherIconAndDegrees(
    modifier: Modifier = Modifier,
    weatherData: CurrentWeatherDataModel
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .weight(5 / 2f)
                .aspectRatio(1f),
            painter = painterResource(id = weatherData.weatherMedia.weatherIcon),
            contentScale = ContentScale.Fit,
            contentDescription = "Weather icon",
        )
        Spacer(modifier = Modifier.weight(1 / 2f))
        AutoResizeableText(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            text = weatherData.temperaturePropertiesModel.tempAverage.toInt().toString() + "°",
            defaultFontSize = 84.sp,
            minFontSize = 48.sp,
            textAlign = TextAlign.Center,
            color = lightModePrimaryTextColor,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
private fun MaxMinFeelsLikeAndWeatherDescription(
    modifier: Modifier = Modifier,
    weatherData: CurrentWeatherDataModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AutoResizeableText(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Normal,
            color = lightModePrimaryTextColor,
            text = "${weatherData.temperaturePropertiesModel.tempMax?.toInt()}°/" +
                    " ${weatherData.temperaturePropertiesModel.tempMin?.toInt()}°" +
                    " Feels like ${weatherData.temperaturePropertiesModel.tempFeelsLike.toInt()}°",
            defaultFontSize = 16.sp, minFontSize = 10.sp
        )
        Spacer(modifier = Modifier.weight(0.1f))
        AutoResizeableText(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = weatherData.weatherCondition,
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Medium,
            color = lightModePrimaryTextColor,
            defaultFontSize = 20.sp, minFontSize = 10.sp
        )
    }
}
