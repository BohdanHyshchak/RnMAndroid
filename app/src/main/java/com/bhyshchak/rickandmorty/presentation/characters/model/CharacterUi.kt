package com.bhyshchak.rickandmorty.presentation.characters.model

import androidx.annotation.StringRes
import com.bhyshchak.rickandmorty.R

data class CharacterUi(
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

enum class CharacterStatusUi(@StringRes val labelRes: Int) {
    Alive(R.string.character_status_alive),
    Dead(R.string.character_status_dead),
    Unknown(R.string.character_status_unknown),
}

enum class CharacterGenderUi(@StringRes val labelRes: Int) {
    Female(R.string.character_gender_female),
    Male(R.string.character_gender_male),
    Genderless(R.string.character_gender_genderless),
    Unknown(R.string.character_gender_unknown),
}


