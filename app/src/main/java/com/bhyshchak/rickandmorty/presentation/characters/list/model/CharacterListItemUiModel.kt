package com.bhyshchak.rickandmorty.presentation.characters.list.model

import androidx.compose.runtime.Immutable
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi
import com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi

@Immutable
data class CharacterListItemUiModel(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: CharacterStatusUi,
    val gender: CharacterGenderUi,
)


