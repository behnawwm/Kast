package com.example.kast.android.ui

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import com.example.kast.android.ui.home.home.HomeScreen
import com.example.kast.android.ui.home.movie_details.MovieDetailScreen
import com.example.kast.android.ui.home.home.AddToListsBottomSheet
import com.example.kast.android.ui.home.home.MovieOptionsBottomSheet
import com.example.kast.android.ui.profile.main_profile.MainProfileScreen
import com.example.kast.android.ui.profile.SettingsScreen
import com.example.kast.android.ui.search.CategorySearchScreen
import com.example.kast.android.ui.search.QuerySearchScreen
import com.example.kast.android.ui.search.SearchScreen
import com.example.kast.android.ui.watchlist.WatchlistScreen
import com.example.kast.domain.model.MovieView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Watchlist : Screen("watchlist")
    object Profile : Screen("profile")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object MovieOptionsBottomSheet : LeafScreen("options/{movieId}/{movieTitle}") {
        fun createRoute(root: Screen, movieId: Long, movieTitle: String): String {
            return "${root.route}/options/$movieId/$movieTitle"
        }
    }

    object MovieAddToListsBottomSheet : LeafScreen("add-to-lists/{movie}") {
        fun createRoute(root: Screen, movie: MovieView): String {
            val movieUri = Uri.encode(Json.encodeToString(movie))
            return "${root.route}/add-to-lists/$movieUri"
        }
    }

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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        addHomeTopLevel(navController)
        addWatchlistTopLevel(navController)
        addSearchTopLevel(navController)
        addProfileTopLevel(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        addHome(navController, Screen.Home)
        addOptionsBottomSheet(navController, Screen.Home)
        addAddToListsBottomSheet(navController, Screen.Home)
        addMovieDetail(navController, Screen.Home)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHome(navController: NavController, root: Screen) {
    composable(
        route = LeafScreen.Home.createRoute(root)
    ) {
        HomeScreen(
            onMovieClick = { movie ->
                navController.navigate(LeafScreen.MovieDetail.createRoute(root, movie.id))
            },
            onMovieLongClick = { movie ->
                navController.navigate(
                    LeafScreen.MovieAddToListsBottomSheet.createRoute(root, movie)
                )
            },
            onOptionsClick = { movie ->
                navController.navigate(
                    LeafScreen.MovieOptionsBottomSheet.createRoute(
                        root,
                        movie.id,
                        movie.title
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.addOptionsBottomSheet(navController: NavController, root: Screen) {
    bottomSheet(
        route = LeafScreen.MovieOptionsBottomSheet.createRoute(root),
        arguments = listOf(
            navArgument("movieTitle") {
                type = NavType.StringType
            }
        )
    ) {
        MovieOptionsBottomSheet(
            it.arguments!!.getLong("movieId"),
            it.arguments!!.getString("movieTitle")!!
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.addAddToListsBottomSheet(navController: NavController, root: Screen) {
    bottomSheet(
        route = LeafScreen.MovieAddToListsBottomSheet.createRoute(root),
        arguments = listOf(
            navArgument("movie") {
                type = MovieType()
            }
        )
    ) {
        val movie = it.arguments!!.getString("movie")!!.let { movie ->
            Json.decodeFromString<MovieView>(movie)
        }
        AddToListsBottomSheet(
            movie,
            navController
        )
    }

}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addMovieDetail(navController: NavController, root: Screen) {
    composable(route = LeafScreen.MovieDetail.createRoute(root)) {
        val movieId = it.arguments!!.getString("movieId")!!.toLong()
        MovieDetailScreen(movieId, navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
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

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addWatchlist(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Watchlist.createRoute(root)) {
        WatchlistScreen()
    }
}


@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addSearchTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Search.route,
        startDestination = LeafScreen.Search.createRoute(Screen.Search)
    ) {
        addSearch(navController, Screen.Search)
        addQuerySearch(navController, Screen.Search)
        addCategorySearch(navController, Screen.Search)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Search.createRoute(root)) {
        SearchScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addQuerySearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.QuerySearch.createRoute(root)) {
        QuerySearchScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addCategorySearch(navController: NavController, root: Screen) {
    composable(route = LeafScreen.CategorySearch.createRoute(root)) {
        CategorySearchScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
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

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addProfile(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Profile.createRoute(root)) {
        MainProfileScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addSettings(navController: NavController, root: Screen) {
    composable(route = LeafScreen.Settings.createRoute(root)) {
        SettingsScreen()
    }
}

