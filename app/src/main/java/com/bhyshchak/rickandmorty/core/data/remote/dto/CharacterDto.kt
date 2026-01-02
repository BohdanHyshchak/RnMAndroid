package com.bhyshchak.rickandmorty.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: String,
    @SerialName("species") val species: String,
    @SerialName("gender") val gender: String,
    @SerialName("image") val image: String,
    @SerialName("origin") val origin: NamedResourceDto,
    @SerialName("location") val location: NamedResourceDto,
    @SerialName("episode") val episode: List<String>,
)

@Serializable
data class NamedResourceDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)


