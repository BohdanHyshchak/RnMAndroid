package com.bhyshchak.rickandmorty.designsystem.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.bhyshchak.rickandmorty.designsystem.theme.DS

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = DS.typography.body,
    color: Color = DS.colors.textPrimary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}


