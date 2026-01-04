package com.bhyshchak.rickandmorty.core.inmemory

import com.bhyshchak.rickandmorty.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DefaultCharactersStore : CharactersStore {
    private val data = MutableStateFlow<Map<Int, Character>>(emptyMap())

    override fun upsertAll(characters: List<Character>) {
        if (characters.isEmpty()) return
        data.update { old ->
            buildMap(old.size + characters.size) {
                putAll(old)
                for (c in characters) {
                    put(c.id, c)
                }
            }
        }
    }

    override fun observe(id: Int): Flow<Character?> =
        data
            .map { it[id] }
            .distinctUntilChanged()
}


