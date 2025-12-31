package com.bhyshchak.rickandmorty.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalAppColors: ProvidableCompositionLocal<AppColors> =
    staticCompositionLocalOf { LightColors }

internal val LocalAppDimens: ProvidableCompositionLocal<AppDimens> =
    staticCompositionLocalOf { DefaultDimens }

internal val LocalAppTypography: ProvidableCompositionLocal<AppTypography> =
    staticCompositionLocalOf { DefaultTypography }

object DS {
    val colors: AppColors
        @Composable get() = LocalAppColors.current

    val dimens: AppDimens
        @Composable get() = LocalAppDimens.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
}


