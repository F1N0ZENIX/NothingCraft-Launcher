package com.nothingtube.data.remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.nothingtube.data.model.VideoItem
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YouTubeApiClient(private val apiKey: String) {
    private val gson = Gson()

    suspend fun search(query: String, musicOnly: Boolean = false): List<VideoItem> =
        withContext(Dispatchers.IO) {
            val q = if (musicOnly) "$query music" else query
            val url = URL(
                "https://www.googleapis.com/youtube/v3/search" +
                    "?part=snippet&type=video&maxResults=25&q=${java.net.URLEncoder.encode(q, "UTF-8")}" +
                    "&key=$apiKey"
            )
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 10_000
            conn.readTimeout = 10_000
            try {
                if (conn.responseCode != 200) return@withContext emptyList()
                val body = conn.inputStream.bufferedReader().readText()
                val resp = gson.fromJson(body, SearchResponse::class.java)
                val ids = resp.items.mapNotNull { it.id?.videoId }
                if (ids.isEmpty()) return@withContext emptyList()
                fetchDetails(ids)
            } finally { conn.disconnect() }
        }

    private suspend fun fetchDetails(ids: List<String>): List<VideoItem> =
        withContext(Dispatchers.IO) {
            val url = URL(
                "https://www.googleapis.com/youtube/v3/videos" +
                    "?part=snippet,contentDetails&id=${ids.joinToString(",")}&key=$apiKey"
            )
            val conn = url.openConnection() as HttpURLConnection
            try {
                if (conn.responseCode != 200) return@withContext emptyList()
                val body = conn.inputStream.bufferedReader().readText()
                val resp = gson.fromJson(body, VideoListResponse::class.java)
                resp.items.map { v ->
                    VideoItem(
                        videoId = v.id,
                        title = v.snippet.title,
                        channel = v.snippet.channelTitle,
                        thumbnail = v.snippet.thumbnails.medium?.url
                            ?: v.snippet.thumbnails.default?.url ?: "",
                        duration = v.contentDetails.duration
                    )
                }
            } finally { conn.disconnect() }
        }
}

data class SearchResponse(val items: List<SearchItem> = emptyList())
data class SearchItem(val id: SearchId? = null)
data class SearchId(val videoId: String? = null)

data class VideoListResponse(val items: List<VideoDetail> = emptyList())
data class VideoDetail(
    val id: String,
    val snippet: Snippet,
    @SerializedName("contentDetails") val contentDetails: ContentDetails
)
data class Snippet(val title: String, val channelTitle: String, val thumbnails: Thumbnails)
data class Thumbnails(val medium: Thumb? = null, val default: Thumb? = null)
data class Thumb(val url: String)
data class ContentDetails(val duration: String)
