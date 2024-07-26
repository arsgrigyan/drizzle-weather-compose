package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.southernsunrise.drizzle.utils.getScreenDimensInDp

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    drawerWidthFraction: Float,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val drawerMinOffsetAbs = -with(LocalDensity.current) { (-360).dp.toPx() }.dp
    val drawerWidth: Dp = (getScreenDimensInDp().first.value * drawerWidthFraction).dp

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .width(drawerWidth)
                    .fillMaxHeight()
                    .clickable(
                        enabled = true,
                        onClickLabel = null,
                        role = null,
                        onClick = {},
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            ) {
                drawerContent()
            }
        },
        scrimColor = Color.DarkGray.copy(0.3f)
    ) {
        Box(
            modifier = Modifier
                .offset(x = (drawerState.offset.value.dp + drawerMinOffsetAbs) * (drawerWidth / drawerMinOffsetAbs))
        ) {
            content()
        }
    }
}
