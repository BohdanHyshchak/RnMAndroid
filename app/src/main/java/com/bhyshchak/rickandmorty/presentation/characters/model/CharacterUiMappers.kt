package com.bhyshchak.rickandmorty.presentation.characters.model

import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.presentation.characters.details.model.CharacterDetailsUiModel
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemUiModel

fun Character.toListItemUiModel(): CharacterListItemUiModel =
    CharacterListItemUiModel(
        id = id,
        name = name,
        species = species,
        imageUrl = imageUrl,
        status = status.toStatusUiModel(),
        gender = gender.toGenderUiModel(),
    )

fun Character.toDetailsUiModel(): CharacterDetailsUiModel =
    CharacterDetailsUiModel(
        id = id,
        name = name,
        species = species,
        imageUrl = imageUrl,
        originName = originName,
        locationName = locationName,
        episodeCount = episodeUrls.size,
        status = status.toStatusUiModel(),
        gender = gender.toGenderUiModel(),
    )

private fun CharacterStatus.toStatusUiModel(): CharacterStatusUi =
    when (this) {
        CharacterStatus.Alive -> CharacterStatusUi.Alive
        CharacterStatus.Dead -> CharacterStatusUi.Dead
        CharacterStatus.Unknown -> CharacterStatusUi.Unknown
    }

private fun CharacterGender.toGenderUiModel(): CharacterGenderUi =
    when (this) {
        CharacterGender.Female -> CharacterGenderUi.Female
        CharacterGender.Male -> CharacterGenderUi.Male
        CharacterGender.Genderless -> CharacterGenderUi.Genderless
        CharacterGender.Unknown -> CharacterGenderUi.Unknown
    }


