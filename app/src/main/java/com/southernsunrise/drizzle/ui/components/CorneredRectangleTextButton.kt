package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily

@Composable
fun CorneredRectangleTextButton(
    modifier: Modifier = Modifier,
    textFontSize:TextUnit = 18.sp,
    textColor: Color = Color.White.copy(0.8f),
    backgroundTint: Color = Color.White.copy(0.5f),
    cornerRadius: Dp = 20.dp
) {
    TextButton(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = cornerRadius))
            .background(backgroundTint).then(modifier),
        onClick = { /*TODO*/ }) {
        Text(
            text = "Manage Locations",
            fontFamily = gothamFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = textFontSize,
            color = textColor
        )
    }
}