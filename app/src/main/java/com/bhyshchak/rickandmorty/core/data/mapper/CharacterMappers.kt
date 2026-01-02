package com.bhyshchak.rickandmorty.core.data.mapper

import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.remote.dto.CharacterDto

private const val EPISODE_DELIMITER = "|"

fun CharacterDto.toEntity(
    page: Int,
    nowMillis: Long = System.currentTimeMillis(),
): CharacterEntity =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        imageUrl = image,
        originName = origin.name,
        locationName = location.name,
        episodeUrls = episode.joinToString(separator = EPISODE_DELIMITER),
        page = page,
        lastUpdatedAtMillis = nowMillis,
    )

fun CharacterDto.toDomain(): Character =
    Character(
        id = id,
        name = name,
        status = status.toDomainStatus(),
        species = species,
        gender = gender.toDomainGender(),
        imageUrl = image,
        originName = origin.name,
        locationName = location.name,
        episodeUrls = episode,
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
        episodeUrls = episodeUrls.toEpisodeUrls(),
    )

private fun String.toEpisodeUrls(): List<String> =
    if (isBlank()) emptyList() else split(EPISODE_DELIMITER)

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

fun CharacterFilters.toQueryParams(): Triple<String?, String?, String?> =
    Triple(
        name?.takeIf { it.isNotBlank() },
        status?.name?.lowercase(),
        gender?.name?.lowercase(),
    )


