package com.bhyshchak.rickandmorty.presentation.characters.model

import androidx.annotation.StringRes
import com.bhyshchak.rickandmorty.R

enum class CharacterStatusUi(@param:StringRes val labelRes: Int) {
    Alive(R.string.character_status_alive),
    Dead(R.string.character_status_dead),
    Unknown(R.string.character_status_unknown),
}

enum class CharacterGenderUi(@param:StringRes val labelRes: Int) {
    Female(R.string.character_gender_female),
    Male(R.string.character_gender_male),
    Genderless(R.string.character_gender_genderless),
    Unknown(R.string.character_gender_unknown),
}


