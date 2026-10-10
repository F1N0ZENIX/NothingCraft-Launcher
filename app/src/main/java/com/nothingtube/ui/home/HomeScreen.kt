package com.nothingtube.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nothingtube.ui.components.NothingDotMatrix
import com.nothingtube.ui.components.NothingVideoCard
import com.nothingtube.ui.theme.*

@Composable
fun HomeScreen(
    onSearch: (String) -> Unit,
    onOpenVideo: (String, String, String, String) -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenHistory: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(32.dp))
            Text("NOTHING", color = NothingGray, style = MaterialTheme.typography.labelSmall)
            Text("TUBE", color = NothingWhite, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            NothingDotMatrix()
            Spacer(Modifier.height(24.dp))
        }
        item {
            OutlinedTextField(
                value = query, onValueChange = { query = it },
                placeholder = { Text("Pesquisar no YouTube...", color = NothingGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NothingWhite,
                    unfocusedBorderColor = NothingDot,
                    focusedTextColor = NothingWhite,
                    unfocusedTextColor = NothingWhite,
                    cursorColor = NothingRed
                ),
                trailingIcon = {
                    TextButton(onClick = { if (query.isNotBlank()) onSearch(query) }) {
                        Text("IR", color = NothingRed)
                    }
                }
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AssistChip(
                    onClick = onOpenFavorites,
                    label = { Text("Favoritos", color = NothingWhite) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = NothingDarkGray)
                )
                AssistChip(
                    onClick = onOpenHistory,
                    label = { Text("Histórico", color = NothingWhite) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = NothingDarkGray)
                )
            }
        }
        item {
            Spacer(Modifier.height(8.dp))
            Text("RECENTES", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        }
        item {
            Surface(
                color = NothingDarkGray, shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Nenhum vídeo recente ainda.", color = NothingGray)
                    Spacer(Modifier.height(4.dp))
                    Text("Use a busca para começar.", color = NothingGray, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}
