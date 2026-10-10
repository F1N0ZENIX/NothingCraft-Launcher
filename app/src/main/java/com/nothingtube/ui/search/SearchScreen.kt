package com.nothingtube.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nothingtube.data.model.VideoItem
import com.nothingtube.ui.components.NothingSearchBar
import com.nothingtube.ui.components.NothingVideoCard
import com.nothingtube.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    apiKey: String,
    onSearch: suspend (String, Boolean) -> List<VideoItem>,
    onOpenVideo: (VideoItem) -> Unit,
    musicMode: Boolean = false
) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<VideoItem>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(32.dp))
        Text(if (musicMode) "MÚSICA" else "BUSCA", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))
        NothingSearchBar(
            value = query, onValueChange = { query = it },
            onSearch = {
                if (query.isBlank()) return@NothingSearchBar
                if (apiKey.isBlank()) {
                    errorMsg = "Chave não configurada. Vá em Ajustes → cole a chave → Salvar."
                } else {
                    loading = true; errorMsg = null; results = emptyList()
                    scope.launch {
                        try {
                            results = onSearch(query, musicMode)
                            if (results.isEmpty()) errorMsg = "Nenhum resultado. Tente outra palavra."
                        } catch (e: Exception) {
                            errorMsg = e.message ?: "Erro desconhecido."
                        } finally { loading = false }
                    }
                }
            }
        )
        Spacer(Modifier.height(16.dp))

        if (apiKey.isBlank()) {
            Surface(color = NothingDarkGray, shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Chave de API necessária", color = NothingWhite, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("1. Abra console.cloud.google.com", color = NothingGray, style = MaterialTheme.typography.bodyMedium)
                    Text("2. Ative a YouTube Data API v3", color = NothingGray, style = MaterialTheme.typography.bodyMedium)
                    Text("3. Crie uma chave de API", color = NothingGray, style = MaterialTheme.typography.bodyMedium)
                    Text("4. Cole em Ajustes → Salvar chave", color = NothingGray, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        errorMsg?.let {
            Spacer(Modifier.height(8.dp))
            Surface(color = NothingDarkGray, shape = MaterialTheme.shapes.medium) {
                Text(it, color = accentColorFor(com.nothingtube.settings.SettingsManager.accentColor),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

        if (loading) {
            Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NothingWhite)
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(results) { item ->
                NothingVideoCard(item = item, onClick = { onOpenVideo(item) })
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}
