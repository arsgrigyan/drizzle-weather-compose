package com.southernsunrise.drizzle.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.southernsunrise.drizzle.network.models.representationWeatherModel.HourlyWeatherDataModel
import com.southernsunrise.drizzle.ui.theme.listOverlayHorizontalGradient
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardBackgroundColorLight
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusSmall

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HourlyForecastCard(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = weatherInfoCardBackgroundColorLight,
    cardCornerRadius: Dp = weatherInfoCardsCornerRadiusDefault,
    todayHoursList: List<HourlyWeatherDataModel>,
    tomorrowHoursList: List<HourlyWeatherDataModel>,
) {
    val horizontalScrollState = rememberScrollState()
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        val toAddFromTomorrowsTime = 24 - todayHoursList.count()

        Box(
            modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topStart = weatherInfoCardsCornerRadiusSmall,
                        topEnd = cardCornerRadius,
                        bottomStart = cardCornerRadius,
                        bottomEnd = cardCornerRadius
                    )
                )
                .background(cardBackgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp)
                    .horizontalScroll(horizontalScrollState),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (hour in todayHoursList) {
                    SingleHourlyWeatherCard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(3 / 4f),
                        iconResource = hour.weatherMedia.weatherIcon,
                        temperature = hour.temperaturePropertiesModel.tempAverage.toInt(),
                        timeString = if (todayHoursList.indexOf(hour) == 0) "Now" else hour.dateTime
                    )
                }
                for (i in 0..toAddFromTomorrowsTime) {
                    SingleHourlyWeatherCard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(3 / 4f),
                        iconResource = tomorrowHoursList[i].weatherMedia.weatherIcon,
                        temperature = tomorrowHoursList[i].temperaturePropertiesModel.tempAverage.toInt(),
                        timeString = tomorrowHoursList[i].dateTime
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = listOverlayHorizontalGradient)
            )
        }
    }
}
