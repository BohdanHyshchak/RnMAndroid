package com.bhyshchak.rickandmorty.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.mapper.toDomain
import com.bhyshchak.rickandmorty.core.data.paging.CharactersPagingSource
import com.bhyshchak.rickandmorty.core.data.paging.CharactersRemoteMediator
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val db: AppDatabase,
) : CharacterRepository {

    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    override fun observePagedCharacters(filters: CharacterFilters): Flow<PagingData<Character>> {
        return if (filters.isEmpty()) {
            // Використовуємо кешування в Room для повного списку
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    initialLoadSize = NETWORK_PAGE_SIZE * 2,
                    enablePlaceholders = false,
                ),
                remoteMediator = CharactersRemoteMediator(api = api, db = db),
                pagingSourceFactory = { db.characterDao().pagingSource() },
            ).flow.map { pagingData ->
                pagingData.map { it.toDomain() }
            }
        } else {
            // Network-only для фільтрованих результатів
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    initialLoadSize = NETWORK_PAGE_SIZE * 2,
                    enablePlaceholders = false,
                ),
                pagingSourceFactory = { CharactersPagingSource(api = api, filters = filters) },
            ).flow
        }
    }

    override fun observeCharacter(id: Int): Flow<Character?> =
        db.characterDao().observeCharacter(id).map { it?.toDomain() }

    private companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}


