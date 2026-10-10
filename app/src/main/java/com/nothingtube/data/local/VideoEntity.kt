package com.nothingtube.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val channel: String,
    val thumbnail: String,
    val duration: String,
    val isFavorite: Boolean = false,
    val lastOpened: Long = 0L
)
