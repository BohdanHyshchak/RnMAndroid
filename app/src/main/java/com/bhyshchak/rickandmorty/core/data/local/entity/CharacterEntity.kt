package com.bhyshchak.rickandmorty.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MVP cache entity. Stores the "full" character payload we need for list + details.
 *
 * Note: Since this is a new DB setup in this branch, we start with version=1.
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val imageUrl: String,
    val originName: String,
    val locationName: String,
    val episodeUrls: String,
    val updatedAtMillis: Long,
)


