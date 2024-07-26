package com.southernsunrise.drizzle.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.data.remote.models.geocodingModel.GeolocationResponseModelItem
import com.southernsunrise.drizzle.data.remote.models.representationWeatherModel.LocationFullWeatherModel
import com.southernsunrise.drizzle.ui.components.AutoResizeableText
import com.southernsunrise.drizzle.ui.components.CorneredRectangleTextButton
import com.southernsunrise.drizzle.ui.components.DrawerLocationItem
import com.southernsunrise.drizzle.ui.theme.darkProgressBarColor
import com.southernsunrise.drizzle.ui.theme.verticalGradientClearSkyDay
import com.southernsunrise.drizzle.utils.GeolocationSearchState
import com.southernsunrise.drizzle.utils.SearchBarState
import com.southernsunrise.drizzle.utils.getScreenHeightFraction
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContentScreen(
    modifier: Modifier = Modifier,
    locationsList: SnapshotStateList<LocationFullWeatherModel>?,
    currentlySelectedLocationIndex: MutableState<Int> = mutableIntStateOf(0),
    background: Brush = verticalGradientClearSkyDay,
    onExistingLocationSelected: (locationName: String) -> Unit,
    onNewLocationSelected: (locationName: String) -> Unit,
    searchBarState: SearchBarState,
    geolocationSearchState: GeolocationSearchState,
) {
    val scope = rememberCoroutineScope()
    val searchJobState = remember { mutableStateOf<Job?>(null) }

    val searchBarFocusRequester = searchBarState.focusRequester
    val searchQuery = searchBarState.searchQuery
    val isSearchBarActive = searchBarState.isSearchBarActive

    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 24.dp,
                    bottomEnd = 14.dp,
                    bottomStart = 0.dp
                )
            )
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(4f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f), onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.8f),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                    contentDescription = "location icon",
                    tint = Color.White.copy(0.8f)
                )
            }

        }


        SearchBar(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 0.dp, max = getScreenHeightFraction(0.35f))
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(25.dp))
                .focusable()
                .focusRequester(searchBarFocusRequester),
            placeholder = { Text(text = "Search location") },
            query = searchQuery.value,
            onQueryChange = { newQuery: String ->
                searchQuery.value = newQuery

                // Cancel the previous job if it's still active
                searchJobState.value?.cancel()

                if (newQuery.isNotEmpty()) {

                    if (geolocationSearchState.isGeolocationLoading.value) {
                        geolocationSearchState.onGeolocationCallCancel()
                    }
                    // Launch a new coroutine for the new query
                    searchJobState.value = scope.launch {
                        delay(600)
                        geolocationSearchState.onGeolocationSearch(newQuery)

                    }
                } else {
                    geolocationSearchState.geolocationResponseData.value.clear()
                }

            },
            onSearch = {
                geolocationSearchState.onGeolocationSearch(it)
            },
            active = isSearchBarActive.value,
            onActiveChange = { isActive: Boolean ->
                isSearchBarActive.value = isActive
            },
            enabled = true,
            colors = SearchBarDefaults.colors(
                containerColor = Color.White.copy(0.65f),
                dividerColor = Color.Black.copy(0.3f),
                inputFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black.copy(0.8f),
                    unfocusedTextColor = Color.Black.copy(0.8f)
                )
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = Color.Black.copy(0.6f)
                )
            },
            trailingIcon = {
                if (isSearchBarActive.value) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (searchQuery.value.isNotBlank()) {
                                searchQuery.value = ""
                                geolocationSearchState.geolocationResponseData.value.clear()
                            } else isSearchBarActive.value = !isSearchBarActive.value
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon",
                        tint = Color.Black.copy(0.6f)
                    )
                }
            },
            interactionSource = remember {
                MutableInteractionSource()
            }
        ) {


            if (!geolocationSearchState.isGeolocationLoading.value) {
                if (geolocationSearchState.geolocationResponseData.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(5.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        geolocationSearchState.geolocationResponseData.value.forEach { item: GeolocationResponseModelItem ->
                            val itemFullName =
                                item.let { "${it.name}, ${if (it.state != null) it.state + ',' else ""} ${it.country}" }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp, top = 5.dp)
                                    .clickable {
                                        onNewLocationSelected(itemFullName)
                                    },
                                text = itemFullName,
                                color = Color.Black.copy(0.6f)
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (searchQuery.value.isBlank()) {
                                "Find new locations"
                            } else if (geolocationSearchState.errorMessage.value.isNullOrEmpty()) {
                                "Something went wrong"
                            } else geolocationSearchState.errorMessage.value ?: "",
                            color = Color.Black.copy(0.6f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = darkProgressBarColor)
                }
            }

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

            if (!locationsList.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(10f), verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_location),
                            contentDescription = "location icon",
                            tint = Color.White.copy(0.8f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        AutoResizeableText(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            text = "Recent Locations",
                            defaultFontSize = 22.sp,
                            color = Color.White.copy(0.6f),
                            fontWeight = FontWeight.Bold,
                            minFontSize = 16.sp,
                            textAlign = TextAlign.Start
                        )
                    }

                    locationsList.forEachIndexed { index: Int, element: LocationFullWeatherModel ->
                        val bgTint: Color
                        val contentTint: Color
                        if (index == currentlySelectedLocationIndex.value) {
                            bgTint = Color.White.copy(0.2f)
                            contentTint = Color.White.copy(0.7f)
                        } else {
                            bgTint = Color.Transparent
                            contentTint = Color.White.copy(0.6f)
                        }

                        DrawerLocationItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(9 / 2f)
                                .padding(10.dp),
                            locationFullWeatherModel = element,
                            textFontSize = 22.sp,
                            textColor = contentTint,
                            iconTint = contentTint,
                            backgroundTint = bgTint,
                            backgroundCornerRadius = 20.dp,
                            onClick = {
                                scope.launch {
                                    onExistingLocationSelected(element.address)
                                }
                            }
                        )
                    }

                    CorneredRectangleTextButton(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .aspectRatio(5.5f)
                            .align(Alignment.CenterHorizontally),
                        backgroundTint = Color.White.copy(0.15f),
                        textColor = Color.White.copy(0.6f),
                        textFontSize = 18.sp,
                        cornerRadius = 25.dp
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
            } else Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Recent searches will appear here.", color = Color.White.copy(0.8f))
            }
    }

}
