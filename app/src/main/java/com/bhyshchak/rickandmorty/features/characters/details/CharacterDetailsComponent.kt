package com.bhyshchak.rickandmorty.features.characters.details

import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsUiEvent
import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsUiState
import kotlinx.coroutines.flow.StateFlow

interface CharacterDetailsComponent {
    val state: StateFlow<CharacterDetailsUiState>

    fun onEvent(event: CharacterDetailsUiEvent)
}


