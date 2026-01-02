package com.bhyshchak.rickandmorty.core.domain.model

data class CharacterFilters(
    val name: String? = null,
    val status: CharacterStatus? = null,
    val gender: CharacterGender? = null,
) {
    fun isEmpty(): Boolean = name.isNullOrBlank() && status == null && gender == null
}


