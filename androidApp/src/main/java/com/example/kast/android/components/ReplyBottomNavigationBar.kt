package com.example.kast.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.kast.android.KastRoutes
import com.example.kast.android.topLevelDestinations

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (KastRoutes) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        topLevelDestinations.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = { navigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = destination.title
                    )
                }
            )
        }
    }
}