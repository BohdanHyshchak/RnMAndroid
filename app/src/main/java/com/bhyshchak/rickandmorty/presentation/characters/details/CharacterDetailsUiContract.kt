package com.bhyshchak.rickandmorty.presentation.characters.details

import androidx.compose.runtime.Immutable
import com.bhyshchak.rickandmorty.presentation.characters.details.model.CharacterDetailsUiModel

@Immutable
data class CharacterDetailsUiState(
    val character: CharacterDetailsUiModel? = null,
)

sealed interface CharacterDetailsUiEvent {
    data object BackClicked : CharacterDetailsUiEvent
}


