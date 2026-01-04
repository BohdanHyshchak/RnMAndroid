package com.bhyshchak.rickandmorty.presentation.characters.list

import androidx.compose.runtime.Immutable
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi

@Immutable
data class CharactersListUiState(
    val searchQuery: String = "",
    val status: CharacterStatusUi? = null,
    val gender: CharacterGenderUi? = null,
)

sealed interface CharactersListUiEvent {
    data class SearchQueryChanged(val query: String) : CharactersListUiEvent
    data class StatusClicked(val current: CharacterStatusUi?) : CharactersListUiEvent
    data object StatusCleared : CharactersListUiEvent
    data class GenderClicked(val current: CharacterGenderUi?) : CharactersListUiEvent
    data object GenderCleared : CharactersListUiEvent

    data class CharacterClicked(val id: Int) : CharactersListUiEvent

    /** UI-only: handled in Route by calling `pagingItems.retry()` */
    data object RetryClicked : CharactersListUiEvent
}


