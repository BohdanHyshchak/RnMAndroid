package com.bhyshchak.rickandmorty.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MVP cache entity. Stores the "full" character payload we need for list + details.
 *
 * Ordering:
 * - `loadedPage` + `indexInPage` preserve the original API order for the base list.
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val loadedPage: Int,
    val indexInPage: Int,
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


