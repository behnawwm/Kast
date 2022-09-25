package com.example.kast.android.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kast.android.ui.home.HomeScreen
import com.example.kast.android.ui.home.MovieDetailScreen
import com.example.kast.android.ui.profile.SettingsScreen
import com.example.kast.android.ui.search.CategorySearchScreen
import com.example.kast.android.ui.search.QuerySearchScreen
import com.example.kast.android.ui.search.SearchScreen

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

    object Search :
        Screen("Search", Icons.Default.Search, Icons.Outlined.Search, "search")
}

private sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object MovieDetail : LeafScreen("movie-detail/{movieId}") {
        fun createRoute(root: Screen, movieId: Long): String {
            return "${root.route}/movie-detail/$movieId"
        }
    }

    object Search : LeafScreen("search")
    object QuerySearch : LeafScreen("query-search")
    object CategorySearch : LeafScreen("category-search")

    object Watchlist : LeafScreen("watchlist")

    object Settings : LeafScreen("settings")
    object Profile : LeafScreen("profile")
}


val topLevelDestinations = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Watchlist,
    Screen.Profile,
)


@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        addHomeTopLevel(navController)
        addWatchlistTopLevel(navController)
        addSearchTopLevel(navController)
        addProfileTopLevel(navController)
    }
}

private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        addHome(navController, Screen.Home)
        addMovieDetail(navController, Screen.Home)
    }
}

fun NavGraphBuilder.addHome(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Home.createRoute(root)) {
        HomeScreen(hiltViewModel(), onMovieClicked = { movie ->
            navController.navigate(LeafScreen.MovieDetail.createRoute(root, movie.id))
        })
//        MovieCategoriesScreen(hiltViewModel(), onMovieClick = {
//            scope.launch {
//                bottomSheetTitle = it.title
//                bottomSheetState.bottomSheetState.expand()
//            }
//        })
    }
}

fun NavGraphBuilder.addMovieDetail(navController: NavController, root: Screen) {
    composable(route = LeafScreen.MovieDetail.createRoute(root)) {
        MovieDetailScreen()
    }
}

private fun NavGraphBuilder.addWatchlistTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Watchlist.route,
        startDestination = LeafScreen.Watchlist.createRoute(Screen.Watchlist)
    ) {
        addWatchlist(navController, Screen.Watchlist)
    }
}

fun NavGraphBuilder.addWatchlist(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Watchlist.createRoute(root)) {
        WatchlistScreen()
    }
}


private fun NavGraphBuilder.addSearchTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Search.createRoute(Screen.Search)
    ) {
        addSearch(navController, Screen.Search)
        addQuerySearch(navController, Screen.Search)
        addCategorySearch(navController, Screen.Search)
    }
}

fun NavGraphBuilder.addSearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Search.createRoute(root)) {
        SearchScreen()
    }
}

fun NavGraphBuilder.addQuerySearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.QuerySearch.createRoute(root)) {
        QuerySearchScreen()
    }
}

fun NavGraphBuilder.addCategorySearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.CategorySearch.createRoute(root)) {
        CategorySearchScreen()
    }
}

private fun NavGraphBuilder.addProfileTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile)
    ) {
        addProfile(navController, Screen.Profile)
        addSettings(navController, Screen.Profile)
    }
}

fun NavGraphBuilder.addProfile(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Profile.createRoute(root)) {
        ProfileScreen()
    }
}

fun NavGraphBuilder.addSettings(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Settings.createRoute(root)) {
        SettingsScreen()
    }
}