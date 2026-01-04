package com.bhyshchak.rickandmorty.presentation.characters.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.bhyshchak.rickandmorty.features.characters.list.CharactersListComponent

@Composable
fun CharactersListRoute(
    component: CharactersListComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState().value
    val pagingItems = component.characters.collectAsLazyPagingItems()

    val onEvent: (CharactersListUiEvent) -> Unit =
        remember(component, pagingItems) {
            { event ->
                when (event) {
                    CharactersListUiEvent.RetryClicked -> pagingItems.retry()
                    else -> component.onEvent(event)
                }
            }
        }

    CharactersListScreen(
        state = state,
        pagingItems = pagingItems,
        onEvent = onEvent,
        modifier = modifier,
    )
}


