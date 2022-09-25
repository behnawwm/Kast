package com.example.kast.android.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kast.android.R
import com.example.kast.android.theme.*
import com.example.kast.android.utils.SetDarkSystemBarColors
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KastContent()
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class
)
@Composable
fun KastContent() {
    KastTheme() {
        SetDarkSystemBarColors(background, bottomNavigationContainerColor)

//        val navController = rememberNavController()
//        val bottomSheetState = rememberBottomSheetScaffoldState()
//        var bottomSheetTitle by remember { mutableStateOf("") }

        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberAnimatedNavController(bottomSheetNavigator)
        val navBackStackEntry by navController.currentBackStackEntryAsState()

//        BottomSheetScaffold(
//            scaffoldState = bottomSheetState,
//            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
//            sheetBackgroundColor = background,
//            sheetPeekHeight = 0.dp,
//            sheetContent = {
//                MovieMoreBottomSheet(bottomSheetTitle)
//            }
//        ) {

        Scaffold(
            bottomBar = {
                val currentSelectedItem by navController.currentScreenAsState()
                AppBottomNavigationBar(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigate(selected.route) {
                            launchSingleTop = true
                            restoreState = true

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) { scaffoldPadding ->
            ModalBottomSheetLayout(bottomSheetNavigator) {
                AppNavigation(
                    navController,
                    modifier = Modifier.padding(scaffoldPadding)
                )
            }
        }
//        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KastContent()
}

@Composable
fun AppBottomNavigationBar(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = bottomNavigationContainerColor
    ) {
        mainNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
                label = {
                    Text(text = stringResource(item.labelResId))
                },
                icon = {
                    MainNavigationItemIcon(
                        item = item,
                        selected = selectedNavigation == item.screen
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

@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Home.route } -> {
                    selectedItem.value = Screen.Home
                }
                destination.hierarchy.any { it.route == Screen.Watchlist.route } -> {
                    selectedItem.value = Screen.Watchlist
                }
                destination.hierarchy.any { it.route == Screen.Search.route } -> {
                    selectedItem.value = Screen.Search
                }
                destination.hierarchy.any { it.route == Screen.Profile.route } -> {
                    selectedItem.value = Screen.Profile
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
private fun MainNavigationItemIcon(item: MainNavigationItem, selected: Boolean) {
    val painter = when (item) {
        is MainNavigationItem.ResourceIcon -> painterResource(item.iconResId)
        is MainNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is MainNavigationItem.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is MainNavigationItem.ImageVectorIcon -> item.selectedImageVector?.let { rememberVectorPainter(it) }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId)
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId)
        )
    }
}

private sealed class MainNavigationItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null
    ) : MainNavigationItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null
    ) : MainNavigationItem(screen, labelResId, contentDescriptionResId)
}

private val mainNavigationItems = listOf(
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Home,
        labelResId = R.string.home_title,
        contentDescriptionResId = R.string.home_title,
        iconImageVector = Icons.Outlined.Home,
        selectedImageVector = Icons.Default.Home
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Search,
        labelResId = R.string.search_title,
        contentDescriptionResId = R.string.search_title,
        iconImageVector = Icons.Outlined.Search,
        selectedImageVector = Icons.Default.Search
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Watchlist,
        labelResId = R.string.watchlist_title,
        contentDescriptionResId = R.string.watchlist_title,
        iconImageVector = Icons.Outlined.Bookmark,
        selectedImageVector = Icons.Default.Bookmark
    ),
    MainNavigationItem.ImageVectorIcon(
        screen = Screen.Profile,
        labelResId = R.string.profile_title,
        contentDescriptionResId = R.string.profile_title,
        iconImageVector = Icons.Outlined.Person,
        selectedImageVector = Icons.Default.Person
    )
)
