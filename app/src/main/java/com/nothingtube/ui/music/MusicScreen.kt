package com.nothingtube.ui.music

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nothingtube.ui.theme.*

@Composable
fun MusicScreen(apiKey: String, onOpenSearch: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(32.dp))
        Text("MÚSICA", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))
        Text("YouTube Music", color = NothingWhite, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            "Busque músicas, videoclipes e artistas usando a mesma chave da YouTube Data API.",
            color = NothingGray
        )
        Spacer(Modifier.height(24.dp))
        if (apiKey.isBlank()) {
            Text("Configure a chave de API nas Configurações para habilitar a busca musical.", color = NothingRed)
        } else {
            Button(
                onClick = onOpenSearch,
                colors = ButtonDefaults.buttonColors(containerColor = NothingWhite, contentColor = NothingBlack)
            ) { Text("ABRIR BUSCA MUSICAL") }
        }
    }
}
