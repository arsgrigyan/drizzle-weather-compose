package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.southernsunrise.drizzle.network.models.representationWeatherModel.DailyWeatherDataModel
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusSmall
import com.southernsunrise.drizzle.utils.formatAsWeekMonthDate


@Composable
fun DailyForecastCard(
    modifier: Modifier = Modifier,
    days: List<DailyWeatherDataModel>,
    cardBackgroundColor: Color,
    cardCornerRadius: Dp = weatherInfoCardsCornerRadiusDefault,
    tint: Color
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        days.forEachIndexed { index: Int, day: DailyWeatherDataModel ->
            val cardTopStartCorner = if (index == 0) weatherInfoCardsCornerRadiusSmall else 0.dp
            val cardTopEndCorner = if (index == 0) cardCornerRadius else 0.dp

            val cardBottomStartCorner =
                if (index == 0) 0.dp else if (index == days.lastIndex) cardCornerRadius else 0.dp
            val cardBottomEndCorner =
                if (index == 0) 0.dp else if (index == days.lastIndex) cardCornerRadius else 0.dp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7f)
                    .clip(
                        RoundedCornerShape(
                            topStart = cardTopStartCorner,
                            topEnd = cardTopEndCorner,
                            bottomStart = cardBottomStartCorner,
                            bottomEnd = cardBottomEndCorner
                        )
                    )
                    .background(cardBackgroundColor)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    color = tint,
                    text = day.dateTime.formatAsWeekMonthDate()
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Row(
                    modifier = Modifier.weight(1.5f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .padding(5.dp),
                        imageVector = ImageVector.vectorResource(day.weatherMedia.weatherIcon),
                        contentDescription = "Weather icon",
                        tint = tint
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textAlign = TextAlign.End,
                        color = tint,
                        text = "${day.temperaturePropertiesModel.tempMax?.toInt()}°/${day.temperaturePropertiesModel.tempMin?.toInt()}°"
                    )
                }

            }
        }
    }
}