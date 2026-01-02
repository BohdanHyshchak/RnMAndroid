package com.bhyshchak.rickandmorty.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhyshchak.rickandmorty.core.data.mapper.toDomain
import com.bhyshchak.rickandmorty.core.data.mapper.toQueryParams
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters

class CharactersPagingSource(
    private val api: RickAndMortyApi,
    private val filters: CharacterFilters,
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1
        val (name, status, gender) = filters.toQueryParams()

        return try {
            val response = api.getCharacters(
                page = page,
                name = name,
                status = status,
                gender = gender,
            )

            val characters = response.results.map { it.toDomain() }
            val nextKey = if (response.info.next == null) null else page + 1
            val prevKey = if (page > 1) page - 1 else null

            LoadResult.Page(
                data = characters,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}


