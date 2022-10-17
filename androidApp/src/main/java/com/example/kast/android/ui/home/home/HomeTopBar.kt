package com.example.kast.android.ui.home.home

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kast.android.R
import com.example.kast.android.theme.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                painterResource(id = R.drawable.kast),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(56.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Person, "")
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Search, "")
            }
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.MoreVert, "")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = background,
        )
    )
}