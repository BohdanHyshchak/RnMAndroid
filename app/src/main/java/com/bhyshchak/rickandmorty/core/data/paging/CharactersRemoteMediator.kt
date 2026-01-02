package com.bhyshchak.rickandmorty.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.local.entity.RemoteKeysEntity
import com.bhyshchak.rickandmorty.core.data.mapper.toEntity
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val api: RickAndMortyApi,
    private val db: AppDatabase,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                // On the initial run, Paging may request APPEND while the PagingState is still empty.
                // Returning "endOfPaginationReached=true" here would permanently stop pagination until the screen is recreated.
                val lastItem = state.lastItemOrNull()
                val remoteKeys = lastItem?.let { db.remoteKeysDao().remoteKeysByCharacterId(it.id) }

                when {
                    remoteKeys?.nextKey != null -> remoteKeys.nextKey
                    remoteKeys?.nextKey == null && remoteKeys != null -> return MediatorResult.Success(endOfPaginationReached = true)
                    else -> {
                        val maxPage = db.characterDao().maxPage() ?: 0
                        maxPage + 1
                    }
                }
            }
        }

        return try {
            val response = api.getCharacters(page = page)
            val endOfPaginationReached = response.info.next == null

            val characterDao = db.characterDao()
            val remoteKeysDao = db.remoteKeysDao()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    characterDao.clearAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val entities = response.results.map { dto ->
                    dto.toEntity(page = page)
                }
                characterDao.insertAll(entities)

                val keys = response.results.map { dto ->
                    RemoteKeysEntity(
                        characterId = dto.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }
                remoteKeysDao.insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}


