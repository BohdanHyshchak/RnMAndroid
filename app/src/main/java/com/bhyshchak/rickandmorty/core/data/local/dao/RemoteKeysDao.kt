package com.bhyshchak.rickandmorty.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhyshchak.rickandmorty.core.data.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM character_remote_keys WHERE characterId = :id")
    suspend fun remoteKeysByCharacterId(id: Int): RemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeysEntity>)

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()
}


