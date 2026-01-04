package com.bhyshchak.rickandmorty.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.bhyshchak.rickandmorty.core.data.local.dao.CharacterDao
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.mapper.toDomain
import com.bhyshchak.rickandmorty.core.data.mapper.toQueryParams
import com.bhyshchak.rickandmorty.core.data.paging.CharactersRemoteMediator
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val database: AppDatabase,
    private val characterDao: CharacterDao,
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun observePagedCharacters(filters: CharacterFilters): Flow<PagingData<Character>> =
        if (filters.isEmpty()) {
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    initialLoadSize = NETWORK_PAGE_SIZE * 2,
                    enablePlaceholders = false,
                ),
                remoteMediator = CharactersRemoteMediator(
                    api = api,
                    database = database,
                    characterDao = characterDao,
                ),
                pagingSourceFactory = { characterDao.pagingSourceBase() },
            ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
        } else {
            val (name, status, gender) = filters.toQueryParams()
            Pager(
                config = PagingConfig(
                    pageSize = LOCAL_PAGE_SIZE,
                    initialLoadSize = LOCAL_PAGE_SIZE * 2,
                    enablePlaceholders = false,
                ),
                pagingSourceFactory = { characterDao.pagingSourceFiltered(name = name, status = status, gender = gender) },
            ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
        }

    override fun observeCharacter(id: Int): Flow<Character?> =
        characterDao
            .observeCharacter(id)
            .map { it?.toDomain() }

    private companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val LOCAL_PAGE_SIZE = 20
    }
}


