package com.bhyshchak.rickandmorty.presentation.characters.model

import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus

fun Character.toUi(): CharacterUi =
    CharacterUi(
        id = id,
        name = name,
        species = species,
        imageUrl = imageUrl,
        originName = originName,
        locationName = locationName,
        episodeCount = episodeUrls.size,
        status = status.toUi(),
        gender = gender.toUi(),
    )

private fun CharacterStatus.toUi(): CharacterStatusUi =
    when (this) {
        CharacterStatus.Alive -> CharacterStatusUi.Alive
        CharacterStatus.Dead -> CharacterStatusUi.Dead
        CharacterStatus.Unknown -> CharacterStatusUi.Unknown
    }

private fun CharacterGender.toUi(): CharacterGenderUi =
    when (this) {
        CharacterGender.Female -> CharacterGenderUi.Female
        CharacterGender.Male -> CharacterGenderUi.Male
        CharacterGender.Genderless -> CharacterGenderUi.Genderless
        CharacterGender.Unknown -> CharacterGenderUi.Unknown
    }


