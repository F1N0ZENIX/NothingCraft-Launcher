package com.nothingtube.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.nothingtube.settings.SettingsManager
import com.nothingtube.ui.theme.*

@Composable
fun SettingsScreen(
    apiKey: String,
    accentColor: String,
    showDots: Boolean,
    compactMode: Boolean,
    musicDefault: Boolean,
    onApiKeyChange: (String) -> Unit,
    onAccentChange: (String) -> Unit,
    onShowDotsChange: (Boolean) -> Unit,
    onCompactModeChange: (Boolean) -> Unit,
    onMusicDefaultChange: (Boolean) -> Unit
) {
    var localKey by remember { mutableStateOf(apiKey) }
    val accent = accentColorFor(accentColor)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(32.dp))
        Text("AJUSTES", color = NothingGray, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(16.dp))

        // ─── CHAVE DE API ───
        SectionTitle("Chave da YouTube Data API v3")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = localKey, onValueChange = { localKey = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("AIza...", color = NothingGray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = accent,
                unfocusedBorderColor = NothingDot,
                focusedTextColor = NothingWhite,
                unfocusedTextColor = NothingWhite,
                cursorColor = accent
            )
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { onApiKeyChange(localKey.trim()) },
            colors = ButtonDefaults.buttonColors(containerColor = NothingWhite, contentColor = NothingBlack),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) { Text("SALVAR CHAVE") }

        Spacer(Modifier.height(8.dp))
        Text(
            "Obtenha grátis em console.cloud.google.com → APIs e serviços → Biblioteca → YouTube Data API v3.",
            color = NothingGray, style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.height(28.dp))

        // ─── APARÊNCIA ───
        SectionTitle("Aparência")

        Spacer(Modifier.height(12.dp))
        Text("Cor de destaque", color = NothingWhite, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(
                "red" to Color(0xFFFF0000),
                "white" to Color(0xFFFFFFFF),
                "orange" to Color(0xFFFF6600),
                "purple" to Color(0xFF9B59B6),
                "blue" to Color(0xFF00A8FF)
            ).forEach { (key, color) ->
                val selected = accentColor == key
                Box(
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selected) 3.dp else 0.dp,
                            color = if (selected) NothingWhite else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { onAccentChange(key) }
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        SwitchRow("Mostrar matriz de pontos", showDots, onShowDotsChange)
        SwitchRow("Modo compacto (listas menores)", compactMode, onCompactModeChange)
        SwitchRow("Buscar música por padrão", musicDefault, onMusicDefaultChange)

        Spacer(Modifier.height(28.dp))

        // ─── SOBRE ───
        SectionTitle("Sobre")
        Spacer(Modifier.height(8.dp))
        Text("Nothing Tube 1.0.0", color = NothingWhite)
        Text("App minimalista inspirado na Nothing OS.", color = NothingGray)
        Spacer(Modifier.height(12.dp))
        Text(
            "Este app não reproduz vídeos em segundo plano (proibido pelos termos do YouTube). A reprodução embutida é experimental.",
            color = accent, style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = NothingWhite, style = MaterialTheme.typography.titleLarge)
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = NothingWhite, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked, onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = NothingWhite,
                checkedTrackColor = NothingGray,
                uncheckedThumbColor = NothingGray,
                uncheckedTrackColor = NothingDarkGray
            )
        )
    }
}
