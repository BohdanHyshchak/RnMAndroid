package com.bhyshchak.rickandmorty.presentation.characters.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent

@Composable
fun CharacterDetailsRoute(
    component: CharacterDetailsComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState().value

    val onEvent: (CharacterDetailsUiEvent) -> Unit =
        remember(component) { { event -> component.onEvent(event) } }

    CharacterDetailsScreen(
        state = state,
        onEvent = onEvent,
        modifier = modifier,
    )
}


