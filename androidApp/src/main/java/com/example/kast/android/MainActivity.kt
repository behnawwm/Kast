package com.example.kast.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.kast.KastApp
import com.example.kast.android.theme.KastTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by remember {
                viewModel.state
            }
            KastTheme {
                MovieCategoriesScreen(state.categories)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    KastTheme {
        MovieCategoriesScreen(FakeData.categories)
    }
}

@Composable
fun MovieCategoriesScreen(categories: List<Category>) {
    if (categories.isEmpty())
        CircularProgressIndicator()
    else
        LazyColumn() {
            items(categories) { category ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    ) {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
                        )
                        Text(
                            text = category.subtitle,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.LightGray,
                            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                    MovieList(
                        movies = category.movies,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                    )
                }
            }
        }
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
            .width(100.dp)
    ) {

        Card(shape = RoundedCornerShape(8.dp)) {
            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .build()
            SubcomposeAsyncImage(
                imageLoader = imageLoader,
                model = movie.imageUrl,
                loading = {
                    CircularProgressIndicator()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = movie.title,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Light
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.MoreVert,
                "more",
                tint = Color.White,
                modifier = Modifier.clickable {

                }
            )
        }

    }

}
