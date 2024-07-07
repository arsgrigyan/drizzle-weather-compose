package com.southernsunrise.drizzle.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.southernsunrise.drizzle.network.NetworkClient
import com.southernsunrise.drizzle.network.models.geocodingModel.GeolocationResponseModel
import com.southernsunrise.drizzle.network.models.geocodingModel.GeolocationResponseModelItem
import com.southernsunrise.drizzle.network.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.network.models.responseWeatherModel.FullWeatherDataResponseModel
import com.southernsunrise.drizzle.utils.GeolocationSearchState
import com.southernsunrise.drizzle.utils.SearchBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityViewModel : ViewModel() {

    val drawerLocationsStateList: SnapshotStateList<LocationFullWeatherModel> = mutableStateListOf()
    val weatherDataLoadingState: MutableState<Boolean> = mutableStateOf(false)
    private val geolocationResponseState: MutableState<GeolocationResponseModel> = mutableStateOf(
        GeolocationResponseModel()
    )

    private val isDrawerSearchBarActive: MutableState<Boolean> = mutableStateOf(false)
    private val searchQuery: MutableState<String> = mutableStateOf("")
    private val focusRequester = FocusRequester()

    val drawerSearchBarState = SearchBarState(
        isSearchBarActive = isDrawerSearchBarActive,
        searchQuery = searchQuery,
        focusRequester = focusRequester
    )

    private val isGeolocationLoading: MutableState<Boolean> = mutableStateOf(false)
    private var geoLocationSearchResultsCall: Call<GeolocationResponseModel>? = null
    private val geolocationSearchCallCancelled: MutableState<Boolean> = mutableStateOf(false)

    val geolocationSearchState: GeolocationSearchState = GeolocationSearchState(
        isGeolocationLoading = isGeolocationLoading,
        geolocationResponseData = geolocationResponseState,
        onGeolocationSearch = { query: String ->
            searchGeolocation(query)
        },
        onGeolocationCallCancel = { cancelGeolocationCall() },
        errorMessage = mutableStateOf(null)
    )




    val currentlySelectedLocationIndex: MutableState<Int> =
        mutableStateOf(0) // by default first location in the list is selected


    private fun searchGeolocation(query: String) {
        isGeolocationLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            geoLocationSearchResultsCall =
                NetworkClient.geocodingApiService.searchLocationByName(query = query)
            geoLocationSearchResultsCall?.enqueue(object : Callback<GeolocationResponseModel> {
                override fun onResponse(
                    call: Call<GeolocationResponseModel>,
                    response: Response<GeolocationResponseModel>
                ) {
                    isGeolocationLoading.value = false
                    val result: GeolocationResponseModel? = response.body()
                    if (response.isSuccessful && result != null) {
                        if (result.isNotEmpty()) {
                            geolocationSearchState.errorMessage.value = null
                            geolocationResponseState.value = result
                        } else geolocationSearchState.errorMessage.value = "No location found"
                        Log.i("search location response: ", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<GeolocationResponseModel>, t: Throwable) {
                    isGeolocationLoading.value = false
                    t.message.let {
                        if (t.message != "Canceled") geolocationSearchState.errorMessage.value = it
                    }
                    Log.e("search location failure: ", t.stackTraceToString())
                }

            })
        }
    }

    fun searchLocationByLatLon(lat: Double, lon: Double) {
        viewModelScope.launch {
            val call = NetworkClient.geocodingApiService.getLocationNameByCoordinates(lat, lon)
            call.enqueue(object : Callback<GeolocationResponseModel> {
                override fun onResponse(
                    call: Call<GeolocationResponseModel>,
                    response: Response<GeolocationResponseModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val locationList: List<GeolocationResponseModelItem> =
                            response.body() as GeolocationResponseModel
                        if (locationList.isNotEmpty()) {
                            val location = locationList.first()
                            val fullCityName: String =
                                location.let { "${it.name}, ${if (it.state != null) it.state + ',' else ""} ${it.country}" }
                            fetchCurrentWeatherData(fullCityName)
                        }
                    }
                }

                override fun onFailure(call: Call<GeolocationResponseModel>, t: Throwable) {
                    geolocationSearchState.errorMessage.value = t.message.toString()
                }

            })
        }
    }

    private fun cancelGeolocationCall() {
        geoLocationSearchResultsCall?.cancel()
        geolocationSearchCallCancelled.value = true
        isGeolocationLoading.value = false
    }


    fun fetchCurrentWeatherData(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDataLoadingState.value = true

            val call: Call<FullWeatherDataResponseModel> =
                NetworkClient.weatherApiService.getFullWeatherDataByLocationName(cityName)
            call.enqueue(object : Callback<FullWeatherDataResponseModel> {

                override fun onResponse(
                    call: Call<FullWeatherDataResponseModel>,
                    response: Response<FullWeatherDataResponseModel>
                ) {
                    weatherDataLoadingState.value = false
                    if (response.isSuccessful && response.body() != null) {

                        val locationFullWeatherModel: LocationFullWeatherModel =
                            (response.body() as FullWeatherDataResponseModel).toLocationFullWeatherModel()

                        // if the fetched location is already there in the list we'll just "update" the
                        // previously loaded one
                        val matchedElement: LocationFullWeatherModel? =
                            drawerLocationsStateList.find { it.resolvedAddress == locationFullWeatherModel.resolvedAddress }
                        if (matchedElement != null) {
                            drawerLocationsStateList[
                                drawerLocationsStateList.indexOf(
                                    matchedElement
                                )
                            ] = locationFullWeatherModel

                            currentlySelectedLocationIndex.value = drawerLocationsStateList.indexOf(
                                locationFullWeatherModel
                            )
                        } else {
                            // if fetched location wasn't already in the list, insert it
                            drawerLocationsStateList.add(0, locationFullWeatherModel)
                            currentlySelectedLocationIndex.value = 0
                        }
                    } else {
                        // handle the error case
                    }
                }

                override fun onFailure(call: Call<FullWeatherDataResponseModel>, t: Throwable) {
                    weatherDataLoadingState.value = false
                    // handle the failure case
                }
            })
        }
    }

}