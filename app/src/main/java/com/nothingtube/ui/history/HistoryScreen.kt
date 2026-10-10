package com.nothingtube.ui.history

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
fun HistoryScreen(
    history: List<VideoEntity>,
    onOpen: (VideoEntity) -> Unit,
    onClear: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(32.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("HISTÓRICO", color = NothingGray, style = MaterialTheme.typography.labelSmall)
            if (history.isNotEmpty()) {
                TextButton(onClick = onClear) { Text("LIMPAR", color = NothingRed) }
            }
        }
        Spacer(Modifier.height(16.dp))
        if (history.isEmpty()) {
            Text("Nenhum vídeo no histórico.", color = NothingGray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(history) { entity ->
                    NothingVideoCard(
                        item = com.nothingtube.data.model.VideoItem(
                            entity.videoId, entity.title, entity.channel, entity.thumbnail, entity.duration
                        ),
                        onClick = { onOpen(entity) }
                    )
                }
            }
        }
    }
}
