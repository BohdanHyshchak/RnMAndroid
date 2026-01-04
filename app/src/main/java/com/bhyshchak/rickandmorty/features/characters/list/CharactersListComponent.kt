package com.bhyshchak.rickandmorty.features.characters.list

import androidx.paging.PagingData
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemUiModel
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListUiEvent
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CharactersListComponent {
    val state: StateFlow<CharactersListUiState>
    val characters: Flow<PagingData<CharacterListItemUiModel>>

    fun onEvent(event: CharactersListUiEvent)
}


