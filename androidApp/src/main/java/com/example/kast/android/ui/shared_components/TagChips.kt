package com.example.kast.android.ui.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.bottomNavigationContainerColor

@Composable
fun TagChips(
    tags: List<TagChipData>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        tags.forEachIndexed { pos, item ->
            Box(
                modifier = Modifier
                    .offset((-6 * pos).dp)
                    .clip(CircleShape)
                    .border(1.dp, bottomNavigationContainerColor, CircleShape)
                    .background(item.backgroundColor)
            ) {
                Icon(
                    item.icon,
                    "",
                    tint = item.iconTint,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

val sampleTagChipDataList = listOf(
    TagChipData(Icons.Default.Bookmark, Color.Blue),
    TagChipData(Icons.Default.Save, Color.Red),
    TagChipData(Icons.Default.Satellite, Color.Green),
)

data class TagChipData(
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconTint: Color = Color.White
)