package com.nothingtube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nothingtube.data.model.VideoItem
import com.nothingtube.ui.theme.*

@Composable
fun NothingVideoCard(item: VideoItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NothingDarkGray)
            .border(0.5.dp, NothingDot, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        if (item.thumbnail.isNotBlank()) {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = item.title,
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f).clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(Modifier.fillMaxWidth().aspectRatio(16f / 9f).background(Color(0xFF111111)))
        }
        Column(Modifier.padding(12.dp)) {
            Text(
                text = item.title, color = NothingWhite,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(text = item.channel, color = NothingGray, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun NothingDotMatrix(modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(3) {
            Box(Modifier.size(4.dp).clip(RoundedCornerShape(50)).background(NothingDot))
        }
    }
}
