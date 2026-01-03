package com.bhyshchak.rickandmorty.presentation.characters.list.model

import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderViewData
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusViewData

data class CharacterListItemViewData(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: CharacterStatusViewData,
    val gender: CharacterGenderViewData,
)


