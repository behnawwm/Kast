package com.example.kast.android.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.R
import com.example.kast.android.data.Category
import com.example.kast.android.data.Movie
import com.example.kast.android.theme.*
import com.example.kast.android.ui.TestViewModel
import com.example.kast.android.ui.home.home.HomeTopBar
import com.example.kast.android.utils.AsyncImage
import com.example.kast.android.utils.addEmptyLines
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TestViewModel,
    onMovieClicked: (movie: Movie) -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar()
        }
    ) { paddingValues ->
        paddingValues
        MovieCategoriesList(viewModel, onMovieClicked)
    }
}

@Composable
fun MovieCategoriesList(
    viewModel: TestViewModel,
    onMovieClick: (Movie) -> Unit,
) {
    val state by remember { viewModel.state }

    if (state.categories.isEmpty()) {
        CircularProgressIndicator()
    } else LazyColumn() {
        items(state.categories) { category ->
            MovieListWithHeader(category, onMovieClick = {
                onMovieClick(it)
            })
        }
    }
}

@Composable
fun MovieListWithHeader(
    category: Category, onMovieClick: (Movie) -> Unit
) {
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
        onMovieClick = {
            onMovieClick(it)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieList(
    movies: List<Movie>, modifier: Modifier, onMovieClick: (Movie) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}


@Composable
fun MovieCard(movie: Movie, onClick: (Movie) -> Unit) {

    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .width(140.dp)
        .clickable {

        }) {

        Card(shape = RoundedCornerShape(8.dp)) {
            Box(contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    model = movie.imageUrl,
//                    loading = {
//                        CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
//                    },
//                    error = {
//                        Image(
//                            painter = painterResource(id = R.drawable.avengers),
//                            contentDescription = ""
//                        )
//                    },
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
                    color = bodyColor, fontWeight = FontWeight.Light
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                scope.launch {
                    onClick(movie)
                }
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    stringResource(id = R.string.more),
                    tint = bodyColor,
                )
            }
        }

    }

}