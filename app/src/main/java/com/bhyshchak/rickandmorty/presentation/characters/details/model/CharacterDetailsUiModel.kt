package com.bhyshchak.rickandmorty.presentation.characters.details.model

import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi

data class CharacterDetailsUiModel(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val originName: String,
    val locationName: String,
    val episodeCount: Int,
    val status: CharacterStatusUi,
    val gender: CharacterGenderUi,
)


