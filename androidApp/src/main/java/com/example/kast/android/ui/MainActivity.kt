package com.example.kast.android.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.kast.android.R
import com.example.kast.android.ui.components.BottomNavigationBar
import com.example.kast.android.data.Category
import com.example.kast.android.data.Movie
import com.example.kast.android.theme.*
import com.example.kast.android.utils.SetDarkSystemBarColors
import com.example.kast.android.utils.addEmptyLines
import com.example.kast.android.utils.getImageLoader
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            KastTheme(darkTheme = true) {
                KastApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KastApp(viewModel: TestViewModel) {
    val state by remember {
        viewModel.state
    }
    SetDarkSystemBarColors(background, bottomNavigationContainerColor)

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNavigationBar(
                currentDestination = currentDestination,
                navigateToTopLevelDestination = { route ->
                    navController.navigate(route.route) {
                        restoreState = true
                    }
                }
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Icon(
                        painterResource(id = R.drawable.kast),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(56.dp)
                    )
                }, navigationIcon = {
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
        },

        ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = KastRoutes.Home.route
        ) {
            composable(KastRoutes.Home.route) {
                MovieCategoriesScreen(state.categories, coroutineScope)
            }
            composable(KastRoutes.Watchlist.route) {
                WatchlistScreen()
            }
            composable(KastRoutes.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    val viewModel = TestViewModel()
    KastTheme(darkTheme = true) {
        KastApp(viewModel)
    }
}

@Composable
fun MovieCategoriesScreen(
    categories: List<Category>,
    coroutineScope: CoroutineScope
) {
    if (categories.isEmpty()) {
        CircularProgressIndicator()
    } else
        LazyColumn() {
            items(categories) { category ->
                MovieListWithHeader(category)
            }
        }
}

@Composable
fun MovieListWithHeader(category: Category) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
    ) {
        Text(
            text = category.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = titleColor,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(
            text = category.subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = subtitleColor,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
    }
    MovieList(
        movies = category.movies,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
    )
}

@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable {

            }
    ) {

        Card(shape = RoundedCornerShape(8.dp)) {
            Box(contentAlignment = Alignment.TopEnd) {
                SubcomposeAsyncImage(
                    imageLoader = LocalContext.current.getImageLoader(),
                    model = movie.imageUrl,
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                    },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.avengers),
                            contentDescription = ""
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 140.dp),
                    contentDescription = movie.title
                )
                Text(
                    text = movie.rating.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(4.dp, 2.dp, 4.dp, 2.dp)
//                        .alpha(0.7f)
                        .background(
                            black50Alpha,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp, 2.dp, 4.dp, 2.dp),
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title.addEmptyLines(1),
                style = TextStyle(
                    color = bodyColor,
                    fontWeight = FontWeight.Light
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.MoreVert,
                stringResource(id = R.string.more),
                tint = bodyColor,
                modifier = Modifier
                    .clickable {

                    }
            )
        }

    }

}
