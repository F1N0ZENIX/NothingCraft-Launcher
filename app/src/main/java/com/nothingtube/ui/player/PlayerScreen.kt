package com.nothingtube.ui.player

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nothingtube.data.model.VideoItem
import com.nothingtube.player.YouTubePlayerLauncher
import com.nothingtube.ui.theme.*

@Composable
fun PlayerScreen(item: VideoItem, onBack: () -> Unit) {
    val context = LocalContext.current
    var webViewMode by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(NothingBlack).padding(20.dp)) {
        Spacer(Modifier.height(32.dp))
        Text("REPRODUÇÃO", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))
        Text(item.title, color = NothingWhite, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(item.channel, color = NothingGray)
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { YouTubePlayerLauncher.openInYouTube(context, item.videoId) },
            colors = ButtonDefaults.buttonColors(containerColor = NothingWhite, contentColor = NothingBlack),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("ABRIR NO YOUTUBE") }

        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = { webViewMode = !webViewMode },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = NothingWhite),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text(if (webViewMode) "FECHAR PLAYER EMBUTIDO" else "TENTAR REPRODUÇÃO EMBUTIDA") }

        if (webViewMode) {
            Spacer(Modifier.height(16.dp))
            Text(
                "⚠️ A reprodução embutida pode não funcionar em todos os vídeos e não suporta segundo plano.",
                color = NothingRed, style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(8.dp))
            AndroidView(
                factory = { ctx ->
                    android.webkit.WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false
                        loadUrl("https://www.youtube.com/embed/${item.videoId}?autoplay=1")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(220.dp)
            )
        }

        Spacer(Modifier.weight(1f))
        TextButton(onClick = onBack) { Text("VOLTAR", color = NothingGray) }
    }
}
