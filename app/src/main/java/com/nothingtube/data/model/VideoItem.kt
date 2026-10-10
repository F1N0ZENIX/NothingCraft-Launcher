package com.nothingtube.data.model

data class VideoItem(
    val videoId: String,
    val title: String,
    val channel: String,
    val thumbnail: String,
    val duration: String = ""
)
