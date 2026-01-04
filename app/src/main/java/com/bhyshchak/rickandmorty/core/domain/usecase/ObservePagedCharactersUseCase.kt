package com.bhyshchak.rickandmorty.core.domain.usecase

import androidx.paging.PagingData
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class ObservePagedCharactersUseCase(
    private val repository: CharacterRepository,
) {
    operator fun invoke(filters: CharacterFilters): Flow<PagingData<Character>> =
        repository.observePagedCharacters(filters)
}


