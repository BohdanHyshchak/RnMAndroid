package com.bhyshchak.rickandmorty.features.characters.details

import com.bhyshchak.rickandmorty.presentation.characters.details.model.CharacterDetailsUiModel
import kotlinx.coroutines.flow.StateFlow

interface CharacterDetailsComponent {
    val state: StateFlow<State>

    fun onIntent(intent: Intent)

    data class State(
        val character: CharacterDetailsUiModel? = null,
    )

    sealed interface Intent {
        data object BackClicked : Intent
    }
}


