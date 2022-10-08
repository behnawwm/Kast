package com.example.kast.android.ui.shared_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.orange
import com.example.kast.android.theme.subtitleColor
import com.example.kast.android.theme.titleColor
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.model.MovieView


@Composable
fun MovieListWithHeader(
    categoryView: CategoryView,
    onMovieClick: (MovieView) -> Unit,
    onMovieLongClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        val maxWidth = maxWidth.value - 30
        Column(modifier = modifier.defaultMinSize(minHeight = 180.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = categoryView.type.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = titleColor,
                    )
                    Text(
                        text = categoryView.subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = subtitleColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                IconButton(onClick = {
                    onMoreClick()
                }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "", tint = Color.White)
                }
            }
            MovieList(
                movies = categoryView.movies,
                onMovieClick = onMovieClick,
                onMovieLongClick = onMovieLongClick,
                onOptionsClick = onOptionsClick,
                maxWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(
    movies: List<MovieView>,
    onMovieClick: (MovieView) -> Unit,
    onMovieLongClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    maxWidth: Float,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty())
        Box(modifier = modifier) {
            CircularProgressIndicator(
                color = orange,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
        }
    else
        LazyRow(
            contentPadding = PaddingValues(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    onOptionsClick = onOptionsClick,
                    modifier
                        .width((maxWidth / 3).dp)
                        .combinedClickable(
                            onClick = {
                                onMovieClick(movie)
                            },
                            onLongClick = {
                                onMovieLongClick(movie)
                            }
                        ),
                )
            }
        }
}

@Preview
@Composable
fun MovieListWithHeaderPreview() {
    MovieListWithHeader(
        categoryView = CategoryView(CategoryType.NowPlaying, "Movies", emptyList()),
        onMovieClick = {},
        onMovieLongClick = {},
        onOptionsClick = {},
        onMoreClick = {})
}

@Preview
@Composable
fun MovieListWithHeaderPreview2() {
    MovieListWithHeader(
        categoryView = CategoryView(
            CategoryType.NowPlaying, "Movies", listOf(
                MovieView(1, "Thor", 1.4, "")
            )
        ),
        onMovieClick = {},
        onMovieLongClick = {},
        onOptionsClick = {},
        onMoreClick = {})
}