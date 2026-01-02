package com.bhyshchak.rickandmorty.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bhyshchak.rickandmorty.core.data.local.dao.CharacterDao
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity

/**
 * MVP Room database.
 *
 * Auto-migration strategy (MVP): for now we rely on the builder's automatic destructive migration
 * when schema changes during development. Later we can switch to proper migrations / autoMigrations
 * once schema JSONs are generated and versioning stabilizes.
 */
@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}


