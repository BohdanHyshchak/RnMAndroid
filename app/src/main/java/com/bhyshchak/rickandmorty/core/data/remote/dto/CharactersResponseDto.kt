package com.bhyshchak.rickandmorty.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDto(
    @SerialName("info") val info: PageInfoDto,
    @SerialName("results") val results: List<CharacterDto>,
)

@Serializable
data class PageInfoDto(
    @SerialName("count") val count: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("next") val next: String? = null,
    @SerialName("prev") val prev: String? = null,
)


