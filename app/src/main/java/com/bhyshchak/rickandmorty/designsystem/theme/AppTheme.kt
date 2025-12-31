package com.bhyshchak.rickandmorty.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: AppColors = if (darkTheme) DarkColors else LightColors,
    dimens: AppDimens = DefaultDimens,
    typography: AppTypography = DefaultTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppDimens provides dimens,
        LocalAppTypography provides typography,
    ) {
        content()
    }
}


