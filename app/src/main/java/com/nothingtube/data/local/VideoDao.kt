package com.nothingtube.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos WHERE isFavorite = 1 ORDER BY title ASC")
    fun getFavorites(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE lastOpened > 0 ORDER BY lastOpened DESC LIMIT 50")
    fun getHistory(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(video: VideoEntity)

    @Query("UPDATE videos SET isFavorite = :fav WHERE videoId = :id")
    suspend fun setFavorite(id: String, fav: Boolean)

    @Query("UPDATE videos SET lastOpened = :time WHERE videoId = :id")
    suspend fun touch(id: String, time: Long)

    @Query("DELETE FROM videos WHERE isFavorite = 0")
    suspend fun clearHistory()

    @Query("DELETE FROM videos WHERE videoId = :id AND isFavorite = 0")
    suspend fun deleteIfNotFavorite(id: String)
}
