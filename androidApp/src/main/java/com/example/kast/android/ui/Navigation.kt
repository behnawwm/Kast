package com.example.kast.android.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    object Home : Screen("Home", Icons.Default.Home, Icons.Outlined.Home, "home")
    object Profile : Screen("Profile", Icons.Default.Person, Icons.Outlined.Person, "profile")
    object Watchlist :
        Screen("Watchlist", Icons.Default.Movie, Icons.Outlined.Movie, "watchlist")
}

val topLevelDestinations = listOf(
    Screen.Home,
    Screen.Watchlist,
    Screen.Profile,
)
