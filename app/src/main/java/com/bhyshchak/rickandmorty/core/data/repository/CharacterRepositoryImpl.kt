package com.bhyshchak.rickandmorty.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bhyshchak.rickandmorty.core.data.paging.CharactersPagingSource
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.core.inmemory.CharactersStore
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val store: CharactersStore,
) : CharacterRepository {

    override fun observePagedCharacters(filters: CharacterFilters): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE * 2,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { CharactersPagingSource(api = api, filters = filters, store = store) },
        ).flow

    override fun observeCharacter(id: Int): Flow<Character?> =
        store.observe(id)

    private companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}


