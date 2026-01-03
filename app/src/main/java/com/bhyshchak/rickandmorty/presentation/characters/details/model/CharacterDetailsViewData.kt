package com.bhyshchak.rickandmorty.presentation.characters.details.model

import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderViewData
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusViewData

data class CharacterDetailsViewData(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val originName: String,
    val locationName: String,
    val episodeCount: Int,
    val status: CharacterStatusViewData,
    val gender: CharacterGenderViewData,
)


