package com.nothingtube.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.nothingtube.ui.theme.*

@Composable
fun SettingsScreen(
    apiKey: String,
    onApiKeyChange: (String) -> Unit
) {
    var localKey by remember { mutableStateOf(apiKey) }

    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(32.dp))
        Text("CONFIGURAÇÕES", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))

        Text("Chave da YouTube Data API v3", color = NothingWhite, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = localKey, onValueChange = { localKey = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("AIza...", color = NothingGray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NothingWhite,
                unfocusedBorderColor = NothingDot,
                focusedTextColor = NothingWhite,
                unfocusedTextColor = NothingWhite,
                cursorColor = NothingRed
            )
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { onApiKeyChange(localKey.trim()) },
            colors = ButtonDefaults.buttonColors(containerColor = NothingWhite, contentColor = NothingBlack)
        ) { Text("SALVAR CHAVE") }

        Spacer(Modifier.height(32.dp))
        Text("Sobre", color = NothingWhite, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Nothing Tube 1.0.0", color = NothingGray)
        Text("App minimalista inspirado na Nothing OS.", color = NothingGray)
        Spacer(Modifier.height(16.dp))
        Text(
            "Este app não reproduz vídeos em segundo plano. Isso viola os Termos de Serviço da API do YouTube. A reprodução embutida é experimental e pode falhar.",
            color = NothingRed, style = MaterialTheme.typography.labelSmall
        )
    }
}
