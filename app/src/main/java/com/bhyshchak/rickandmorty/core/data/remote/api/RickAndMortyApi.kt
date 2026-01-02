package com.bhyshchak.rickandmorty.core.data.remote.api

import com.bhyshchak.rickandmorty.core.data.remote.dto.CharactersResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("gender") gender: String? = null,
    ): CharactersResponseDto
}


