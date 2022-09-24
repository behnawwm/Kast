package com.example.kast.android.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.ui.graphics.vector.ImageVector

sealed class KastRoutes(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    object Home : KastRoutes("Home", Icons.Default.Home, Icons.Outlined.Home, "home")
    object Profile : KastRoutes("Profile", Icons.Default.Person, Icons.Outlined.Person, "profile")
    object Watchlist :
        KastRoutes("Watchlist", Icons.Default.Movie, Icons.Outlined.Movie, "watchlist")
}

val topLevelDestinations = listOf(
    KastRoutes.Home,
    KastRoutes.Watchlist,
    KastRoutes.Profile,
)
