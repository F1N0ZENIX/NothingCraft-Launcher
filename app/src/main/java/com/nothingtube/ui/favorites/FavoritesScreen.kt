package com.nothingtube.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nothingtube.data.local.VideoEntity
import com.nothingtube.ui.components.NothingVideoCard
import com.nothingtube.ui.theme.*

@Composable
fun FavoritesScreen(
    favorites: List<VideoEntity>,
    onOpen: (VideoEntity) -> Unit,
    onRemove: (VideoEntity) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(32.dp))
        Text("FAVORITOS", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))
        if (favorites.isEmpty()) {
            Text("Nenhum favorito salvo.", color = NothingGray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favorites) { entity ->
                    NothingVideoCard(
                        item = com.nothingtube.data.model.VideoItem(
                            entity.videoId, entity.title, entity.channel, entity.thumbnail, entity.duration
                        ),
                        onClick = { onOpen(entity) }
                    )
                    TextButton(onClick = { onRemove(entity) }) {
                        Text("REMOVER", color = NothingRed, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
