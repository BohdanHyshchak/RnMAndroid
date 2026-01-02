package com.bhyshchak.rickandmorty.core.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val gender: CharacterGender,
    val imageUrl: String,
    val originName: String,
    val locationName: String,
    val episodeUrls: List<String>,
)

enum class CharacterStatus {
    Alive,
    Dead,
    Unknown,
}

enum class CharacterGender {
    Female,
    Male,
    Genderless,
    Unknown,
}


