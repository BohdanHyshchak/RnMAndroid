package com.bhyshchak.rickandmorty.presentation.characters.model

import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.presentation.characters.details.model.CharacterDetailsViewData
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemViewData

fun Character.toListItemViewData(): CharacterListItemViewData =
    CharacterListItemViewData(
        id = id,
        name = name,
        species = species,
        imageUrl = imageUrl,
        status = status.toStatusViewData(),
        gender = gender.toGenderViewData(),
    )

fun Character.toDetailsViewData(): CharacterDetailsViewData =
    CharacterDetailsViewData(
        id = id,
        name = name,
        species = species,
        imageUrl = imageUrl,
        originName = originName,
        locationName = locationName,
        episodeCount = episodeUrls.size,
        status = status.toStatusViewData(),
        gender = gender.toGenderViewData(),
    )

private fun CharacterStatus.toStatusViewData(): CharacterStatusViewData =
    when (this) {
        CharacterStatus.Alive -> CharacterStatusViewData.Alive
        CharacterStatus.Dead -> CharacterStatusViewData.Dead
        CharacterStatus.Unknown -> CharacterStatusViewData.Unknown
    }

private fun CharacterGender.toGenderViewData(): CharacterGenderViewData =
    when (this) {
        CharacterGender.Female -> CharacterGenderViewData.Female
        CharacterGender.Male -> CharacterGenderViewData.Male
        CharacterGender.Genderless -> CharacterGenderViewData.Genderless
        CharacterGender.Unknown -> CharacterGenderViewData.Unknown
    }


