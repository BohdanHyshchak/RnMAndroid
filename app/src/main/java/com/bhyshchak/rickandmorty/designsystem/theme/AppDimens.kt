package com.bhyshchak.rickandmorty.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimens(
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val corner: Dp,
)

val DefaultDimens = AppDimens(
    xs = 4.dp,
    s = 8.dp,
    m = 16.dp,
    l = 24.dp,
    xl = 32.dp,
    corner = 12.dp,
)


