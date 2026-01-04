package com.bhyshchak.rickandmorty.designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bhyshchak.rickandmorty.designsystem.theme.DS

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DS.colors.background)
            .systemBarsPadding()
            .padding(DS.dimens.m),
        content = content,
    )
}


