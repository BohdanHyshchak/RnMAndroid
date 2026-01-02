package com.bhyshchak.rickandmorty.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bhyshchak.rickandmorty.core.data.local.dao.CharacterDao
import com.bhyshchak.rickandmorty.core.data.local.dao.RemoteKeysDao
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.local.entity.RemoteKeysEntity

@Database(
    entities = [
        CharacterEntity::class,
        RemoteKeysEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}


