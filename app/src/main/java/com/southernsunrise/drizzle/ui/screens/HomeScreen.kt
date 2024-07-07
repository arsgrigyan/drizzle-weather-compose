package com.southernsunrise.drizzle.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.network.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.ui.components.AtmosphericPropertiesCard
import com.southernsunrise.drizzle.ui.components.AutoResizeableText
import com.southernsunrise.drizzle.ui.components.DailyForecastCard
import com.southernsunrise.drizzle.ui.components.HourlyForecastCard
import com.southernsunrise.drizzle.ui.components.SunriseSunsetCard
import com.southernsunrise.drizzle.ui.components.TemperatureMainCard
import com.southernsunrise.drizzle.ui.components.WeatherSectionsTitleText
import com.southernsunrise.drizzle.ui.theme.appBarMaxTitleFontSize
import com.southernsunrise.drizzle.ui.theme.appBarMinTitleFontSize
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily
import com.southernsunrise.drizzle.ui.theme.lightModePrimaryTextColor
import com.southernsunrise.drizzle.ui.theme.lightProgressBarColor
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardBackgroundColorLight
import com.southernsunrise.drizzle.ui.theme.weatherInfoCardsCornerRadiusDefault
import com.southernsunrise.drizzle.utils.Constants.MY_GITHUB_PAGE_URL
import com.southernsunrise.drizzle.utils.Constants.VISUAL_CROSSING_URL
import com.southernsunrise.drizzle.utils.extractHourStringFromTimeString
import com.southernsunrise.drizzle.utils.locationServicesEnabled
import com.southernsunrise.drizzle.utils.openURL
import com.southernsunrise.drizzle.utils.sliceFromCurrentHour


@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    locationWeatherData: LocationFullWeatherModel?,
    isWeatherDataLoading: MutableState<Boolean>,
    context: Context,
    onDrawerStateChange: (activateSearchBar: Boolean) -> Unit,
    onUserLocationAccessed: (lat: Double, lon: Double) -> Unit
) {

    // Create a permission launcher
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, get the location
                    if (locationServicesEnabled(context)) {
                        getCurrentLocation(context) { lat, long ->
                            val location = "Latitude: $lat, Longitude: $long"
                            Log.d("Location: ", location)
                            onUserLocationAccessed(lat, long)
                        }
                    } else Toast.makeText(
                        context,
                        "location services not enabled.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

    Scaffold(
        modifier = modifier,
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        topBar = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.07f)
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onDrawerStateChange(false)
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_more_actions),
                            contentDescription = "More actions",
                            tint = Color.White.copy(0.9f)
                        )
                    }

                    AutoResizeableText(
                        modifier = Modifier.weight(4f),
                        text = locationWeatherData?.resolvedAddress ?: "",
                        defaultFontSize = appBarMaxTitleFontSize,
                        color = lightModePrimaryTextColor,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End,
                        fontFamily = gothamFontFamily,
                        minFontSize = appBarMinTitleFontSize
                    )

                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (hasLocationPermission(context)) {
                                // Permission already granted, access the location
                                getCurrentLocation(context) { lat, long ->
                                    val location = "Latitude: $lat, Longitude: $long"
                                    Log.i("current location: ", location)
                                    onUserLocationAccessed(lat, long)
                                }
                            } else {
                                // Request location permission
                                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                            contentDescription = "Location",
                            tint = Color.White.copy(0.9f)
                        )
                    }
                }
            }
        },
    ) { innerPadding ->

        if (isWeatherDataLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = lightProgressBarColor)
            }
        } else {
            locationWeatherData?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding())
                ) {
                    val verticalScrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(verticalScrollState)
                            .padding(top = 5.dp, bottom = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        TemperatureMainCard(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .aspectRatio(7 / 5f),
                            cardBackgroundColor = weatherInfoCardBackgroundColorLight,
                            cardCornerRadius = weatherInfoCardsCornerRadiusDefault,
                            currentWeatherData = it.weatherModel.currentWeatherDataModel
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            WeatherSectionsTitleText(
                                modifier = Modifier
                                    .fillMaxWidth(0.45f)
                                    .aspectRatio(5f),
                                text = "Hourly forecast",
                                backgroundColor = weatherInfoCardBackgroundColorLight
                            )
                            HourlyForecastCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(5 / 2f),
                                todayHoursList = it.weatherModel.fifteenDaysWeatherDataModel.days[0].hours.sliceFromCurrentHour(
                                    extractHourStringFromTimeString(it.weatherModel.currentWeatherDataModel.dateTimeString)
                                ),
                                tomorrowHoursList = it.weatherModel.fifteenDaysWeatherDataModel.days[0].hours,
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            WeatherSectionsTitleText(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .aspectRatio(9 / 2f),
                                text = "Next 10 days",
                                backgroundColor = weatherInfoCardBackgroundColorLight
                            )
                            DailyForecastCard(
                                modifier = Modifier.fillMaxWidth(),
                                days = it.weatherModel.fifteenDaysWeatherDataModel.days.subList(
                                    0,
                                    10
                                ),
                                cardBackgroundColor = weatherInfoCardBackgroundColorLight,
                                tint = Color.White.copy(0.9f)
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            WeatherSectionsTitleText(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .aspectRatio(5f),
                                text = "Current conditions",
                                backgroundColor = weatherInfoCardBackgroundColorLight
                            )
                            AtmosphericPropertiesCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(3 / 2f),
                                cardCornerRadius = weatherInfoCardsCornerRadiusDefault,
                                cardBackgroundColor = weatherInfoCardBackgroundColorLight,
                                atmosphericPropertiesModel = it.weatherModel.currentWeatherDataModel.atmosphericPropertiesModel
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            WeatherSectionsTitleText(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .aspectRatio(9 / 2f),
                                text = "Astronomical",
                                backgroundColor = weatherInfoCardBackgroundColorLight
                            )
                            SunriseSunsetCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(7 / 2f),
                                cardBackgroundColor = weatherInfoCardBackgroundColorLight,
                                cardCornerRadius = weatherInfoCardsCornerRadiusDefault,
                                astronomicalPropertiesModel = it.weatherModel.currentWeatherDataModel.astronomicalPropertiesModel,
                            )
                        }

                        val annotatedString = buildAnnotatedString {

                            withStyle(
                                style = SpanStyle(
                                    color = Color.White.copy(0.4f),
                                    fontFamily = gothamFontFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append("Powered by ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White.copy(0.5f),
                                    fontFamily = gothamFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                pushStringAnnotation(
                                    tag = "visual_crossing_api",
                                    annotation = "VisualCrossing API"
                                )
                                append("VisualCrossing API")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White.copy(0.4f),
                                    fontFamily = gothamFontFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append("\n created with \uD83E\uDD0D by ")
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color.White.copy(0.5f),
                                    fontFamily = gothamFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                pushStringAnnotation(
                                    tag = "github_page",
                                    annotation = "Arsen"
                                )
                                append("Arsen")
                            }
                        }
                        val uriHandler = LocalUriHandler.current

                        ClickableText(text = annotatedString, style = TextStyle(
                            textAlign = TextAlign.Center
                        ),

                            onClick = { offset ->
                                annotatedString.getStringAnnotations(offset, offset)
                                    .firstOrNull()?.let { span ->
                                        when (span.item) {
                                            "VisualCrossing API" -> {
                                                uriHandler.openUri(VISUAL_CROSSING_URL)
                                            }

                                            "Arsen" -> {
                                                uriHandler.openUri(MY_GITHUB_PAGE_URL)
                                            }
                                        }
                                    }
                            })

                    }

                }
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding()),
                contentAlignment = Alignment.Center
            ) {

                val annotatedString = buildAnnotatedString {

                    withStyle(
                        style = SpanStyle(
                            color = Color.White.copy(0.8f),
                            fontFamily = gothamFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append("No location is currently selected\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontFamily = gothamFontFamily,
                            fontWeight = FontWeight.Medium
                        ),
                    ) {
                        pushStringAnnotation(tag = "search", annotation = "Search")
                        append("Search")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White.copy(0.8f),
                            fontFamily = gothamFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(" or ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontFamily = gothamFontFamily,
                            fontWeight = FontWeight.Medium,
                        ),

                        ) {
                        pushStringAnnotation(
                            tag = "add_location",
                            annotation = "use current location"
                        )
                        append("use current location")
                    }
                }


                ClickableText(text = annotatedString, style = TextStyle(
                    textAlign = TextAlign.Center
                ),
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(offset, offset)
                            .firstOrNull()?.let { span ->
                                when (span.item) {
                                    "Search" -> {
                                        onDrawerStateChange(true)
                                    }

                                    "use current location" -> {
                                        if (hasLocationPermission(context)) {
                                            // Permission already granted, access the location
                                            if (locationServicesEnabled(context)) {
                                                getCurrentLocation(context) { lat, long ->
                                                    val location =
                                                        "Latitude: $lat, Longitude: $long"
                                                    Log.i("current location: ", location)
                                                    onUserLocationAccessed(lat, long)
                                                }
                                            } else Toast.makeText(
                                                context,
                                                "location services not enabled.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            // Request location permission
                                            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                                        }
                                    }
                                }
                            }
                    })

            }
        }

    }

}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            } else Toast.makeText(
                context,
                "Accessing your current location went wrong.",
                Toast.LENGTH_LONG
            ).show()

        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            // TO BE IMPLEMENTED
            exception.printStackTrace()
        }
}




