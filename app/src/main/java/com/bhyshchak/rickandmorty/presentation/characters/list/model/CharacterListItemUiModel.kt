package com.bhyshchak.rickandmorty.presentation.characters.list.model

import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi

data class CharacterListItemUiModel(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: CharacterStatusUi,
    val gender: CharacterGenderUi,
)


