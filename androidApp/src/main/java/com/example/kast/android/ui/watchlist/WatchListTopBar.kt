package com.example.kast.android.ui.watchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Grid3x3
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListTopBar(isExpanded: Boolean, onToggleExpandClick: (isExpanded: Boolean) -> Unit) {
    CenterAlignedTopAppBar(
        title = {},
        actions = {
            IconButton(onClick = {
                onToggleExpandClick(!isExpanded)
            }) {
                Icon(
                    if (isExpanded) Icons.Default.Grid3x3 else Icons.Default.ViewList,
                    ""
                )
            }
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.BarChart, "")
            }
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Sort, "")
            }
        })
}