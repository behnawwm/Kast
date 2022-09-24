package com.example.kast.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.kast.android.theme.*
import com.example.kast.android.ui.KastRoutes
import com.example.kast.android.ui.topLevelDestinations

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (KastRoutes) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = bottomNavigationContainerColor) {
        topLevelDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                onClick = { navigateToTopLevelDestination(destination) },
                label = {
                    Text(text = destination.title)
                },
                icon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = destination.title
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = bottomNavigationSelectedIconColor,
                    selectedTextColor = bottomNavigationSelectedTextColor,
                    indicatorColor = bottomNavigationSelectedIconBackground,
                    unselectedIconColor = bottomNavigationUnselectedTextColor,
                    unselectedTextColor = bottomNavigationUnselectedTextColor,
                )
            )
        }
    }
}