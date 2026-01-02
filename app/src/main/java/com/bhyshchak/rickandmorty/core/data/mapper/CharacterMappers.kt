package com.bhyshchak.rickandmorty.core.data.mapper

import com.bhyshchak.rickandmorty.core.data.remote.dto.CharacterDto
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus

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


