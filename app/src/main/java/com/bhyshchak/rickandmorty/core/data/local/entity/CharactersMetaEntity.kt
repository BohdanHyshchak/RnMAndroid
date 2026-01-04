package com.bhyshchak.rickandmorty.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_meta")
data class CharactersMetaEntity(
    @PrimaryKey val id: Int = 0,
    val lastRefreshEpochMs: Long,
)


