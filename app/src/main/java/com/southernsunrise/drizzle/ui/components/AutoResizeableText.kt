package com.southernsunrise.drizzle.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.southernsunrise.drizzle.ui.theme.gothamFontFamily

@Composable
fun AutoResizeableText(
    modifier: Modifier = Modifier,
    text: String,
    defaultFontSize: TextUnit,
    minFontSize: TextUnit,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    fontFamily: FontFamily = gothamFontFamily,
    fontWeight: FontWeight = FontWeight.Light,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = MaterialTheme.typography.bodySmall.copy(
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = defaultFontSize,
    ),
    color: Color = style.color,
) {

    var shouldDrawText by remember {
        mutableStateOf(false)
    }
    var resizedTextStyle by remember {
        mutableStateOf(style.copy(fontSize = defaultFontSize, color = color))
    }
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Text(
            modifier = Modifier.fillMaxWidth().drawWithContent { if (shouldDrawText) drawContent() },
            text = text,
            color = color,
            softWrap = false,
            maxLines = maxLines,
            style = resizedTextStyle,
            textAlign = textAlign,
            overflow = overflow,
            onTextLayout = { result: TextLayoutResult ->

                if ((result.didOverflowHeight || result.didOverflowWidth) && resizedTextStyle.fontSize.value >= minFontSize.value) {
                    resizedTextStyle =
                        resizedTextStyle.copy(
                            textAlign = textAlign,
                            fontSize = resizedTextStyle.fontSize * 0.95
                        )
                } else {
                    shouldDrawText = true
                }
            },
        )
    }
}