package com.bhyshchak.rickandmorty.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhyshchak.rickandmorty.core.data.local.entity.CharacterEntity
import com.bhyshchak.rickandmorty.core.data.local.entity.CharactersMetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY loadedPage ASC, indexInPage ASC")
    fun pagingSourceBase(): PagingSource<Int, CharacterEntity>

    @Query(
        """
        SELECT * FROM characters
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
          AND (:status IS NULL OR status = :status)
          AND (:gender IS NULL OR gender = :gender)
        ORDER BY loadedPage ASC, indexInPage ASC
        """
    )
    fun pagingSourceFiltered(
        name: String?,
        status: String?,
        gender: String?,
    ): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun observeCharacter(id: Int): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun count(): Int

    @Query("SELECT MAX(loadedPage) FROM characters")
    suspend fun maxLoadedPage(): Int?

    @Query("SELECT lastRefreshEpochMs FROM characters_meta WHERE id = 0")
    suspend fun lastRefreshEpochMs(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeta(meta: CharactersMetaEntity)

    @Query("DELETE FROM characters_meta")
    suspend fun clearMeta()
}


