package com.nothingtube.data.repository

import com.nothingtube.data.local.VideoDao
import com.nothingtube.data.local.VideoEntity
import com.nothingtube.data.model.VideoItem
import com.nothingtube.data.remote.YouTubeApiClient
import kotlinx.coroutines.flow.Flow

class VideoRepository(private val dao: VideoDao) {
    fun favorites(): Flow<List<VideoEntity>> = dao.getFavorites()
    fun history(): Flow<List<VideoEntity>> = dao.getHistory()

    suspend fun saveVideo(item: VideoItem, favorite: Boolean = false) {
        dao.upsert(
            VideoEntity(
                videoId = item.videoId,
                title = item.title,
                channel = item.channel,
                thumbnail = item.thumbnail,
                duration = item.duration,
                isFavorite = favorite
            )
        )
    }

    suspend fun toggleFavorite(item: VideoItem, fav: Boolean) {
        dao.upsert(
            VideoEntity(
                videoId = item.videoId,
                title = item.title,
                channel = item.channel,
                thumbnail = item.thumbnail,
                duration = item.duration,
                isFavorite = fav
            )
        )
    }

    suspend fun markOpened(item: VideoItem) {
        dao.upsert(
            VideoEntity(
                videoId = item.videoId,
                title = item.title,
                channel = item.channel,
                thumbnail = item.thumbnail,
                duration = item.duration,
                lastOpened = System.currentTimeMillis()
            )
        )
    }

    suspend fun clearHistory() = dao.clearHistory()

    suspend fun search(query: String, apiKey: String, musicOnly: Boolean = false): List<VideoItem> {
        if (apiKey.isBlank()) return emptyList()
        return YouTubeApiClient(apiKey).search(query, musicOnly)
    }
}
