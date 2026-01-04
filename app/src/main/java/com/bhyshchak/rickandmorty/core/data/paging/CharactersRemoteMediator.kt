package com.bhyshchak.rickandmorty.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bhyshchak.rickandmorty.core.data.local.dao.CharacterDao
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.local.entity.CharactersMetaEntity
import com.bhyshchak.rickandmorty.core.data.mapper.toEntity
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val api: RickAndMortyApi,
    private val database: AppDatabase,
    private val characterDao: CharacterDao,
    private val cacheTtlMillis: Long = DEFAULT_CACHE_TTL_MILLIS,
    private val nowMillis: () -> Long = { System.currentTimeMillis() },
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cachedCount = characterDao.count()
        if (cachedCount == 0) return InitializeAction.LAUNCH_INITIAL_REFRESH

        val lastRefreshEpochMs = characterDao.lastRefreshEpochMs() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        val ageMillis = nowMillis() - lastRefreshEpochMs
        return if (ageMillis < cacheTtlMillis) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        val page =
            when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> (characterDao.maxLoadedPage() ?: 0) + 1
            }

        return try {
            val response = api.getCharacters(page = page)
            val now = nowMillis()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.clearAll()
                    characterDao.clearMeta()
                }

                val entities =
                    response.results.mapIndexed { indexInPage, dto ->
                        dto.toEntity(
                            loadedPage = page,
                            indexInPage = indexInPage,
                            nowMillis = now,
                        )
                    }

                characterDao.upsertAll(entities)

                if (loadType == LoadType.REFRESH) {
                    characterDao.upsertMeta(CharactersMetaEntity(lastRefreshEpochMs = now))
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (t: Throwable) {
            val hasCache =
                runCatching { characterDao.count() > 0 }
                    .getOrDefault(false)

            when (loadType) {
                LoadType.REFRESH ->
                    if (hasCache) {
                        // Offline with cache: show cached data and avoid full-screen error.
                        MediatorResult.Success(endOfPaginationReached = false)
                    } else {
                        MediatorResult.Error(t)
                    }

                LoadType.APPEND ->
                    if (hasCache) {
                        // Offline with cache: stop further appends without showing hard error.
                        MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        MediatorResult.Error(t)
                    }

                LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    private companion object {
        // MVP default: 1 hour. We can tune later or make it configurable.
        const val DEFAULT_CACHE_TTL_MILLIS: Long = 60L * 60L * 1000L
    }
}


