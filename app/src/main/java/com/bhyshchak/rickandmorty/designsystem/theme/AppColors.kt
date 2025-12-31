package com.bhyshchak.rickandmorty.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val brand: Color,
    val onBrand: Color,
    val background: Color,
    val surface: Color,
    val border: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val error: Color,
    val disabled: Color,
    val onDisabled: Color,
)

val LightColors = AppColors(
    brand = Color(0xFF1B5E20),
    onBrand = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    border = Color(0xFFBDBDBD),
    textPrimary = Color(0xFF111111),
    textSecondary = Color(0xFF444444),
    error = Color(0xFFB3261E),
    disabled = Color(0xFFE0E0E0),
    onDisabled = Color(0xFF757575),
)

val DarkColors = AppColors(
    brand = Color(0xFF66BB6A),
    onBrand = Color(0xFF0B1F0D),
    background = Color(0xFF0F1210),
    surface = Color(0xFF171B18),
    border = Color(0xFF2A302C),
    textPrimary = Color(0xFFE6E6E6),
    textSecondary = Color(0xFFBDBDBD),
    error = Color(0xFFFF5449),
    disabled = Color(0xFF2A302C),
    onDisabled = Color(0xFF7A827C),
)


