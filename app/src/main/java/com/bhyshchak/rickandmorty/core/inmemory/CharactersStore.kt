package com.bhyshchak.rickandmorty.core.inmemory

import com.bhyshchak.rickandmorty.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersStore {
    fun upsertAll(characters: List<Character>)
    fun observe(id: Int): Flow<Character?>
}


