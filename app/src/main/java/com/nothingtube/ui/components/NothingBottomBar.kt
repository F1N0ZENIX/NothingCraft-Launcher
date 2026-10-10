package com.nothingtube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nothingtube.ui.theme.*

data class BottomDest(val route: String, val label: String, val icon: ImageVector)

val bottomDestinations = listOf(
    BottomDest("home", "Início", Icons.Default.Home),
    BottomDest("search", "Busca", Icons.Default.Search),
    BottomDest("favorites", "Favoritos", Icons.Default.FavoriteBorder),
    BottomDest("history", "Histórico", Icons.Default.History),
    BottomDest("music", "Música", Icons.Default.MusicNote),
    BottomDest("settings", "Ajustes", Icons.Default.Settings)
)

@Composable
fun NothingBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().background(NothingBlack).padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomDestinations.forEach { dest ->
            val selected = currentRoute == dest.route
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onNavigate(dest.route) }.padding(8.dp)
            ) {
                Icon(
                    dest.icon, contentDescription = dest.label,
                    tint = if (selected) NothingWhite else NothingGray
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    dest.label,
                    color = if (selected) NothingWhite else NothingGray,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
