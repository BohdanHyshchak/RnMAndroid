package com.bhyshchak.rickandmorty.core.domain.repository

import androidx.paging.PagingData
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun observePagedCharacters(filters: CharacterFilters): Flow<PagingData<Character>>
    fun observeCharacter(id: Int): Flow<Character?>
}


