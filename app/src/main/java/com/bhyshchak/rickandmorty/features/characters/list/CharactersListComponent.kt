package com.bhyshchak.rickandmorty.features.characters.list

import androidx.paging.PagingData
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CharactersListComponent {
    val state: StateFlow<State>
    val characters: Flow<PagingData<CharacterListItemViewData>>

    fun onIntent(intent: Intent)

    data class State(
        val filters: CharacterFilters = CharacterFilters(),
        val searchQuery: String = "",
        val listScrollIndex: Int = 0,
        val listScrollOffset: Int = 0,
    )

    sealed interface Intent {
        data class OpenDetails(val id: Int) : Intent
        data class UpdateSearchQuery(val query: String) : Intent
        data class UpdateStatusFilter(val status: CharacterStatus?) : Intent
        data class UpdateGenderFilter(val gender: CharacterGender?) : Intent
        data class UpdateScrollPosition(val index: Int, val offset: Int) : Intent
    }
}


