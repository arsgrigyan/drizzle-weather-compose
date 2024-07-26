package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AtmosphericPropertiesModel
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardBackgroundColorLight
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusSmall

@Composable
fun AtmosphericPropertiesCard(
    modifier: Modifier = Modifier,
    atmosphericPropertiesModel: com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.AtmosphericPropertiesModel,
    cardCornerRadius: Dp = weatherInfoCardsCornerRadiusDefault,
    cardBackgroundColor: Color = weatherInfoCardBackgroundColorLight
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleWeatherInfoCardVertical(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = weatherInfoCardsCornerRadiusSmall,
                            topEnd = cardCornerRadius,
                            bottomStart = 0.dp,
                            bottomEnd = cardCornerRadius
                        )
                    )
                    .background(cardBackgroundColor)
                    .padding(all = 15.dp),
                iconResource = R.drawable.ic_uv_index,
                title = "UV Index",
                timeString = atmosphericPropertiesModel.uvIndex.toString()
            )
            SingleWeatherInfoCardVertical(
                modifier = Modifier
                    .weight(1f)
                   /* .clip(shape = RoundedCornerShape(cardCornerRadius))*/
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = cardCornerRadius,
                            topEnd = cardCornerRadius,
                            bottomStart = cardCornerRadius,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(cardBackgroundColor)
                    .padding(all = 15.dp),
                iconResource = R.drawable.ic_pressure,
                title = "Pressure",
                timeString = atmosphericPropertiesModel.pressure.toString()
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleWeatherInfoCardVertical(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = cardCornerRadius,
                            bottomStart = cardCornerRadius,
                            bottomEnd = cardCornerRadius
                        )
                    )
                    .background(cardBackgroundColor)
                    .padding(all = 15.dp),
                iconResource = R.drawable.ic_wind,
                title = "Wind",
                timeString = "${atmosphericPropertiesModel.windSpeed.toInt()}km/h"
            )
            SingleWeatherInfoCardVertical(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(cardCornerRadius))
                    .background(cardBackgroundColor)
                    .padding(all = 15.dp),
                iconResource = R.drawable.ic_humidity,
                title = "Humidity",
                timeString = "${atmosphericPropertiesModel.humidity.toInt()}%"
            )
            SingleWeatherInfoCardVertical(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = cardCornerRadius,
                            topEnd = 0.dp,
                            bottomStart = cardCornerRadius,
                            bottomEnd = cardCornerRadius
                        )
                    )
                    .background(cardBackgroundColor)
                    .padding(all = 15.dp),
                iconResource = R.drawable.ic_visibility,
                title = "Visibility",
                timeString = "${atmosphericPropertiesModel.visibility.toInt()}km"
            )
        }

    }


}