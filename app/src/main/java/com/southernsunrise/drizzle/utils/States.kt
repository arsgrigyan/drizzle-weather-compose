package com.southernsunrise.drizzle.utils

import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import com.southernsunrise.drizzle.network.models.geocodingModel.GeolocationResponseModel

data class GeolocationSearchState(
    var isGeolocationLoading: MutableState<Boolean>,
    var geolocationResponseData: MutableState<GeolocationResponseModel>,
    val onGeolocationSearch: (query: String) -> Unit,
    val onGeolocationCallCancel: () -> Unit,
    var errorMessage: MutableState<String?>
)

data class SearchBarState(
    val isSearchBarActive: MutableState<Boolean>,
    val searchQuery: MutableState<String>,
    val focusRequester: FocusRequester,
)