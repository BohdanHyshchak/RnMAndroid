package com.bhyshchak.rickandmorty.features.characters.details

import com.bhyshchak.rickandmorty.core.domain.model.Character
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsComponent {
    val state: StateFlow<State>
    val character: Flow<Character?>

    fun onIntent(intent: Intent)

    data class State(
        val character: Character? = null,
    )

    sealed interface Intent {
        data object BackClicked : Intent
    }
}


