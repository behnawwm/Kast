package com.example.kast.android.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.background
import com.example.kast.android.theme.orange

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Profile",color = orange, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "NOT IMPLEMENTED YET")
        }
    }
}