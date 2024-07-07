package com.southernsunrise.drizzle.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.southernsunrise.drizzle.viewModel.ActivityViewModel
import com.southernsunrise.drizzle.navigation.routes.NavigationRoutes
import com.southernsunrise.drizzle.network.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.ui.components.Drawer
import com.southernsunrise.drizzle.ui.screens.DrawerContentScreen
import com.southernsunrise.drizzle.ui.screens.HomeScreen
import com.southernsunrise.drizzle.ui.theme.DrizzleTheme
import com.southernsunrise.drizzle.ui.theme.verticalGradientClearSkyDay
import com.southernsunrise.drizzle.utils.GeolocationSearchState
import com.southernsunrise.drizzle.utils.SearchBarState
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // To draw the layout behind status bar
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {

            val navController: NavHostController = rememberNavController()
            val viewModel: ActivityViewModel = viewModel()

            // Set status bar appearance to dark status bar
            WindowCompat.getInsetsController(
                window,
                LocalView.current
            ).isAppearanceLightStatusBars = false

            App(navController, viewModel)
        }
    }
}

@Composable
fun App(
    navController: NavHostController,
    viewModel: ActivityViewModel
) {

    val drawerLocationsStateList: SnapshotStateList<LocationFullWeatherModel> =
        viewModel.drawerLocationsStateList
    val currentlySelectedLocationIndex: MutableState<Int> = viewModel.currentlySelectedLocationIndex
    val isWeatherDataLoading: MutableState<Boolean> = viewModel.weatherDataLoadingState
    val onFetchWeatherData: (locationName: String) -> Unit = { locationName: String ->
        viewModel.fetchCurrentWeatherData(locationName)
    }
    val searchBarState: SearchBarState = viewModel.drawerSearchBarState
    val geolocationSearchState: GeolocationSearchState = viewModel.geolocationSearchState
    val onUserLocationAccessed: (Double, Double) -> Unit = { lat, lon: Double ->
        viewModel.searchLocationByLatLon(lat, lon)
    }

    val appBackgroundBrush = if (drawerLocationsStateList.isEmpty()) {
        verticalGradientClearSkyDay
    } else {
        drawerLocationsStateList[currentlySelectedLocationIndex.value].weatherModel
            .currentWeatherDataModel.weatherMedia.appBackground
    }
    val coroutineScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)



    // clear search bar focus, query and response when the drawer closes
    if (drawerState.isClosed) {
        searchBarState.isSearchBarActive.value = false
        searchBarState.searchQuery.value = ""
        geolocationSearchState.geolocationResponseData.value.clear()
    }


    NavHost(
        navController,
        startDestination = NavigationRoutes.MainWeatherInfoScreen.route,
        modifier = Modifier
            .fillMaxSize()
            .background(
                appBackgroundBrush
            )
    ) {
        composable(route = NavigationRoutes.MainWeatherInfoScreen.route) {


            Drawer(
                modifier = Modifier.fillMaxSize(),
                drawerState = drawerState,
                drawerWidthFraction = 0.85f,
                drawerContent = {
                    DrawerContentScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            // Drawer composable has system bars padding and NavHost doesn't
                            // this will ensure that when the drawer is opened the system bars will also be dimmed
                            .systemBarsPadding(),
                        geolocationsList = drawerLocationsStateList,
                        currentlySelectedLocationIndex = currentlySelectedLocationIndex,
                        background = appBackgroundBrush,
                        onNewLocationSelected = { locationName: String ->
                            coroutineScope.launch {
                                drawerState.close()
                                onFetchWeatherData(locationName)
                            }
                        },
                        searchBarState = searchBarState,
                        geolocationSearchState = geolocationSearchState,
                    )
                }
            ) { // drawer's default content is HomeScreen
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    navController = navController,
                    onDrawerStateChange = { activateSearchBar: Boolean ->
                        coroutineScope.launch {
                            if (activateSearchBar) {
                                drawerState.open()
                                searchBarState.isSearchBarActive.value = true
                                searchBarState.focusRequester.requestFocus()
                            } else {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                    // searchBarState.value = false
                                } else drawerState.open()
                            }
                        }
                    },
                    isWeatherDataLoading = isWeatherDataLoading,
                    context = LocalContext.current,
                    locationWeatherData =
                    if (drawerLocationsStateList.isNotEmpty()) drawerLocationsStateList[currentlySelectedLocationIndex.value] else null,
                    onUserLocationAccessed = onUserLocationAccessed
                )
            }
        }
        composable(route = NavigationRoutes.DailyForecastScreen.route) {
            // Daily forecast screen not implemented yet!
            //   DailyForecastScreen(navController = navController)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPrev() {
    DrizzleTheme {
        // HomeScreen()
    }
}
