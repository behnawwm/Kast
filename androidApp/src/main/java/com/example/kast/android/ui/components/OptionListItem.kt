package com.example.kast.android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.android.R
import com.example.kast.android.theme.bodyColor

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
        Text(text = title, color = bodyColor, fontWeight = FontWeight.Bold)

    }
}