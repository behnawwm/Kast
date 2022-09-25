package com.example.kast.android.ui.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.theme.bodyColor


@Composable
fun MovieMoreBottomSheet(bottomSheetTitle: String) {
    Column(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
    ) {
        Text(
            text = bottomSheetTitle,
            color = bodyColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(12.dp, 8.dp, 12.dp, 8.dp)
        )
        OptionListItem(title = "Add to", icon = Icons.Default.Menu)
        OptionListItem(title = "Open With", icon = Icons.Default.OpenInNew)
        OptionListItem(title = "Watch Providers", icon = Icons.Default.LiveTv)
        OptionListItem(title = "All ratings", icon = Icons.Default.Favorite)
        OptionListItem(title = "Share", icon = Icons.Default.Share)
        OptionListItem(title = "Hide", icon = Icons.Default.HideSource)
    }
}

@Preview
@Composable
fun OptionListItem(
    title: String = "",
    icon: ImageVector = Icons.Default.More
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 12.dp, 16.dp, 12.dp),
    ) {
        Icon(
            icon,
            title,
            tint = bodyColor,
            modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 0.dp)
        )
        androidx.compose.material.Text(text = title, color = bodyColor, fontWeight = FontWeight.Bold)

    }
}