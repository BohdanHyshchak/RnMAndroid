package com.bhyshchak.rickandmorty.core.data.mapper

import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.remote.dto.CharacterDto
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus

private const val EPISODE_URLS_SEPARATOR = "|"

fun CharacterDto.toEntity(
    loadedPage: Int,
    indexInPage: Int,
    nowMillis: Long,
): CharacterEntity =
    CharacterEntity(
        id = id,
        loadedPage = loadedPage,
        indexInPage = indexInPage,
        name = name,
        status = status.trim().lowercase(),
        species = species,
        gender = gender.trim().lowercase(),
        imageUrl = image,
        originName = origin.name,
        locationName = location.name,
        episodeUrls = episode.joinToString(separator = EPISODE_URLS_SEPARATOR),
        updatedAtMillis = nowMillis,
    )

fun CharacterEntity.toDomain(): Character =
    Character(
        id = id,
        name = name,
        status = status.toDomainStatus(),
        species = species,
        gender = gender.toDomainGender(),
        imageUrl = imageUrl,
        originName = originName,
        locationName = locationName,
        episodeUrls = episodeUrls.takeIf { it.isNotBlank() }?.split(EPISODE_URLS_SEPARATOR) ?: emptyList(),
    )

private fun String.toDomainStatus(): CharacterStatus =
    when (trim().lowercase()) {
        "alive" -> CharacterStatus.Alive
        "dead" -> CharacterStatus.Dead
        else -> CharacterStatus.Unknown
    }

private fun String.toDomainGender(): CharacterGender =
    when (trim().lowercase()) {
        "female" -> CharacterGender.Female
        "male" -> CharacterGender.Male
        "genderless" -> CharacterGender.Genderless
        else -> CharacterGender.Unknown
    }


