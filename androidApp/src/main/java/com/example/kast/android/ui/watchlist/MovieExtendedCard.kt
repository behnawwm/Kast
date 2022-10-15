package com.example.kast.android.ui.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.kast.android.utils.PreviewFakeData.sampleMovieList
import com.example.kast.android.theme.black50Alpha
import com.example.kast.android.theme.orange
import com.example.kast.android.ui.shared_components.TagChips
import com.example.kast.android.utils.addEmptyLines
import com.example.kast.domain.model.MovieView

@Preview
@Composable
fun MovieExtendedCardPreview() {
    MovieExtendedCard(sampleMovieList[0], {}, {})
}


@Composable
fun MovieExtendedCard(
    movie: MovieView,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .clickable {
            onMovieClick(movie)
        }
        .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()    //todo remove the space below image
                .widthIn(max = 120.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(150.dp)
            ) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                ) {
//                    SubcomposeAsyncImage(
//                        model = movie.imageUrl,
//                        loading = {
//                            androidx.compose.material.CircularProgressIndicator(
//                                color = orange,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp)
//                            )
//                        },
//                        error = {
//                            Image(Icons.Default.BrokenImage, contentDescription = "")
//                        },
//                        modifier = Modifier
//                            .fillMaxHeight(),
//                        contentDescription = movie.title
//                    )
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
            TagChips(movie, modifier = Modifier
                .align(Alignment.End)
                .offset(y = (-8).dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            IconButton(
                onClick = { onOptionsClick(movie) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onOptionsClick(movie)
                    }
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    "",
                    tint = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 40.dp)
            ) {
                Text(
                    movie.title.addEmptyLines(1),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Action, Thriller", style = MaterialTheme.typography.bodyMedium) //todo
            }
            Text(
                text = "30/9/22", //todo
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(Shapes().medium)
                    .background(Color.White.copy(0.3f))
                    .padding(16.dp, 4.dp, 16.dp, 4.dp)
                    .clickable {
                        //todo open datePicker
                    }
            )

        }

    }

}

