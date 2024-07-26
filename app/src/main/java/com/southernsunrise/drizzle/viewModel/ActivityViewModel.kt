package com.southernsunrise.drizzle.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.southernsunrise.drizzle.data.local.WeatherEntity
import com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModel
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.data.remote.models.responseWeatherModel.FullWeatherDataResponseModel
import com.southernsunrise.drizzle.data.repository.WeatherRepository
import com.southernsunrise.drizzle.utils.GeolocationSearchState
import com.southernsunrise.drizzle.utils.SearchBarState
import com.southernsunrise.drizzle.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val drawerLocationsStateList: SnapshotStateList<LocationFullWeatherModel> =
        mutableStateListOf()
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
        focusRequester = focusRequester,
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
        mutableIntStateOf(-1) // by default no location is selected


    fun selectLocation(locationAddress: String) {

        val location: LocationFullWeatherModel? =
            drawerLocationsStateList.find { it.address == locationAddress }

        // If location is already added new request is not made
        // location will be selected from the already loaded list
        // otherwise get request is made to retrieve weather info for the new location
        if (location != null) {
            currentlySelectedLocationIndex.value = drawerLocationsStateList.indexOf(location)
            // this part of code makes sure only one item is marked "current" at a time
            setEntityAsCurrent(location.toWeatherEntity())
        }
    }


    fun loadAlreadySavedLocationsDataIfExist(context: Context) {
        viewModelScope.launch {
            // this set is a workaround against recursive "fetchCurrentWeatherDataViaNetwork" calls
            // fetchCurrentWeatherDataViaNetwork also updates the WeatherEntity in the database
            // causing database state change before .collect returns which therefore results in
            // .collect{} function running infinitely
            val processedLocations = mutableSetOf<String>()

            weatherDataLoadingState.value = true
            delay(2000)
            weatherRepository.getLocationsSavedWeatherData()
                .collect { it: List<WeatherEntity> ->
                    if (it.isNotEmpty()) {
                        if (isNetworkAvailable(context)) {
                            it.forEach { weatherEntity ->

                                if (!processedLocations.contains(weatherEntity.address)) {
                                    processedLocations.add(weatherEntity.address)

                                    fetchCurrentWeatherData(
                                        locationName = weatherEntity.address,
                                        selectLocationAsCurrent = weatherEntity.isCurrent
                                    )
                                }
                            }

                        } else {

                            val list = it.map { item -> item.toLocationFullWeatherModel() }
                            currentlySelectedLocationIndex.value = list.indexOf(
                                weatherRepository.getCurrent()?.toLocationFullWeatherModel()
                            )
                            drawerLocationsStateList.addAll(list)
                            weatherDataLoadingState.value = false

                        }
                    } else weatherDataLoadingState.value = false

                }
        }
    }

    fun fetchCurrentWeatherData(
        locationName: String,
        selectLocationAsCurrent: Boolean = false
    ) {
        weatherDataLoadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val call: Call<FullWeatherDataResponseModel> =
                weatherRepository.getFullWeatherDataByLocationName(
                    locationName
                )

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
                            drawerLocationsStateList.find { it.address == locationFullWeatherModel.address }

                        if (matchedElement != null) {

                            drawerLocationsStateList[
                                drawerLocationsStateList.indexOf(
                                    matchedElement
                                )
                            ] = locationFullWeatherModel

                            val weatherEntity = locationFullWeatherModel.toWeatherEntity()

                            // also update the data in local database
                            viewModelScope.launch {
                                weatherRepository.update(
                                    weatherEntity
                                )
                            }

                                //update currently selected location's isCurrent state to true and the other's to false
                                setEntityAsCurrent(weatherEntity)
                                currentlySelectedLocationIndex.value =
                                    drawerLocationsStateList.indexOf(
                                        locationFullWeatherModel
                                    )



                        } else {
                            // if fetched location wasn't already in the list, insert it in the first position
                            // so if there already were locations saved locally the currently selected location
                            // will be inserted at index of 0 so will come on top of the locations list.
                            drawerLocationsStateList.add(0, locationFullWeatherModel)

                            // also insert it in local database
                            viewModelScope.launch {
                                weatherRepository.insertWeatherData(
                                    locationFullWeatherModel.toWeatherEntity()
                                        .apply { isCurrent = selectLocationAsCurrent }.also {
                                            if (selectLocationAsCurrent)
                                                currentlySelectedLocationIndex.value = 0
                                        }
                                )
                            }
                        }
                    } else {
                        Log.i("somethingWrongWithWeatherFetch", response.toString())
                    }
                }

                override fun onFailure(call: Call<FullWeatherDataResponseModel>, t: Throwable) {
                    weatherDataLoadingState.value = false
                    // handle the failure case
                }
            })
        }
    }

    private fun setEntityAsCurrent(entity: WeatherEntity) {

        viewModelScope.launch {

            // the previously selected location is now "not current"
            val prevEntity = weatherRepository.getCurrent()
            prevEntity?.let {
                weatherRepository.update(it.apply { isCurrent = false })
            }

            weatherRepository.update(entity.apply { isCurrent = true })
        }

    }


    private fun searchGeolocation(query: String) {
        isGeolocationLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            geoLocationSearchResultsCall =
                weatherRepository.searchLocationByName(query = query)
            geoLocationSearchResultsCall?.enqueue(object :
                Callback<GeolocationResponseModel> {
                override fun onResponse(
                    call: Call<GeolocationResponseModel>,
                    response: Response<GeolocationResponseModel>
                ) {
                    isGeolocationLoading.value = false
                    val result: GeolocationResponseModel? =
                        response.body()
                    if (response.isSuccessful && result != null) {
                        if (result.isNotEmpty()) {
                            geolocationSearchState.errorMessage.value = null
                            geolocationResponseState.value = result
                        } else geolocationSearchState.errorMessage.value = "No location found"
                        Log.i("search location response: ", response.body().toString())
                    }
                }

                override fun onFailure(
                    call: Call<GeolocationResponseModel>,
                    t: Throwable
                ) {
                    isGeolocationLoading.value = false
                    when (t.message) {

                        "Canceled" -> {
                            // geolocationSearchState.errorMessage.value = "Canceled"
                        }

                        "Unable to resolve host \"api.openweathermap.org\": No address associated with hostname" -> {
                            geolocationSearchState.errorMessage.value =
                                "Check your connection and try again"
                        }

                        else -> {
                            geolocationSearchState.errorMessage.value =
                                t.message.toString()
                        }

                    }
                    Log.e("location search failure: ", t.message.toString())
                }

            })
        }
    }

    private fun cancelGeolocationCall() {
        geoLocationSearchResultsCall?.cancel()
        geolocationSearchCallCancelled.value = true
        isGeolocationLoading.value = false
    }

    fun searchLocationByLatLon(lat: Double, lon: Double) {
        viewModelScope.launch {
            val call =
                weatherRepository.getLocationNameByCoordinates(lat, lon)
            call.enqueue(object :
                Callback<GeolocationResponseModel> {
                override fun onResponse(
                    call: Call<GeolocationResponseModel>,
                    response: Response<GeolocationResponseModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val locationList: List<com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModelItem> =
                            response.body() as GeolocationResponseModel
                        if (locationList.isNotEmpty()) {
                            val location = locationList.first()
                            val fullLocationName: String =
                                location.let { "${it.name}, ${if (it.state != null) it.state + ',' else ""} ${it.country}" }
                            fetchCurrentWeatherData(
                                locationName = fullLocationName,
                                selectLocationAsCurrent = true
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<GeolocationResponseModel>,
                    t: Throwable
                ) {
                    geolocationSearchState.errorMessage.value = t.message.toString()
                }

            })
        }
    }


}