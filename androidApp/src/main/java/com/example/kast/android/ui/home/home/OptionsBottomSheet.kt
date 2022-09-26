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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.theme.bodyColor
import com.example.kast.android.theme.bottomNavigationContainerColor


@Composable
fun MovieOptionsBottomSheet(movieId: Long, movieTitle: String) {
    Column(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
    ) {
        Text(
            text = movieTitle,
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

@Composable
fun OptionListItem(
    title: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 12.dp, 16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            title,
            tint = bodyColor,
            modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 0.dp)
        )
        Text(
            text = title,
            color = bodyColor,
            fontWeight = FontWeight.Bold,
        )

    }
}

@Preview
@Composable
fun OptionListItemPreview() {
    OptionListItem("title", Icons.Default.Movie)
}