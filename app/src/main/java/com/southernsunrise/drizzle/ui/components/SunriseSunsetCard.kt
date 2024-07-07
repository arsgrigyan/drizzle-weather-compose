package com.southernsunrise.drizzle.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.southernsunrise.drizzle.network.models.representationWeatherModel.AstronomicalPropertiesModel
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardBackgroundColorLight
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusMedium
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusSmall
import com.southernsunrise.drizzle.utils.clipSeconds

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SunriseSunsetCard(
    modifier: Modifier = Modifier,
    astronomicalPropertiesModel: AstronomicalPropertiesModel,
    cardBackgroundColor: Color = weatherInfoCardBackgroundColorLight,
    cardCornerRadius: Dp = weatherInfoCardsCornerRadiusDefault
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingleWeatherInfoCardVertical(
            modifier = Modifier
                .weight(2f)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = weatherInfoCardsCornerRadiusSmall,
                        topEnd = weatherInfoCardsCornerRadiusMedium,
                        bottomStart = cardCornerRadius,
                        bottomEnd = weatherInfoCardsCornerRadiusMedium
                    )
                )
                .background(cardBackgroundColor)
                .padding(all = 10.dp),
            iconResource = R.drawable.ic_sunrise,
            title = "Sunrise",
            timeString = clipSeconds(astronomicalPropertiesModel.sunriseTimeSting)
        )
        SingleWeatherInfoCardVertical(
            modifier = Modifier
                .weight(2f)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = weatherInfoCardsCornerRadiusMedium,
                        topEnd = cardCornerRadius,
                        bottomStart = weatherInfoCardsCornerRadiusMedium,
                        bottomEnd = cardCornerRadius
                    )
                )
                .background(cardBackgroundColor)
                .padding(all = 10.dp),
            iconResource = R.drawable.ic_sunset,
            title = "Sunset",
            timeString = clipSeconds(astronomicalPropertiesModel.sunsetTimeString)
        )

    }
}