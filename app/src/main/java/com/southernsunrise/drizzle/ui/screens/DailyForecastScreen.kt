package com.southernsunrise.drizzle.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.southernsunrise.drizzle.R
import com.southernsunrise.drizzle.ui.components.AutoResizeableText
import com.southernsunrise.drizzle.ui.theme.appBarMaxTitleFontSize
import com.southernsunrise.drizzle.ui.theme.appBarMinTitleFontSize
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyForecastScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(
        modifier = modifier,
        contentColor = Color.Transparent,
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                ),
                title = {
                    AutoResizeableText(
                        text = "Weekly Forecast",
                        defaultFontSize = appBarMaxTitleFontSize,
                        minFontSize = appBarMinTitleFontSize,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        fontFamily = gothamFontFamily,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "Navigate up",
                            modifier = Modifier.rotate(180f),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {

            val verticalScrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScrollState)
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
              // content here
            }
        }

    }

}

@Preview()
@Composable
fun weeklyForecastPreview() {
    //WeeklyForecastScreen()
}