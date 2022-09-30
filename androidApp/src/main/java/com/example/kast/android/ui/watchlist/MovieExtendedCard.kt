package com.example.kast.android.ui.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.FakeData.sampleMovieList
import com.example.kast.android.theme.black50Alpha
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.example.kast.android.utils.AsyncImage
import com.example.kast.android.utils.addEmptyLines
import com.example.kast.data.model.MovieView

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
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clickable {
            onMovieClick(movie)
        }
        .then(modifier)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopEnd,
            ) {
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
                TagChips(
                    tags = testChipData,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                )
            }
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

@Composable
fun TagChips(
    tags: List<TagChipData>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        tags.forEachIndexed { pos, item ->
            Box(
                modifier = Modifier
                    .offset((-6 * pos).dp)
                    .clip(CircleShape)
                    .border(1.dp, bottomNavigationContainerColor, CircleShape)
                    .background(item.backgroundColor)
            ) {
                Icon(
                    item.icon,
                    "",
                    tint = item.iconTint,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

val testChipData = listOf(
    TagChipData(Icons.Default.Bookmark, Color.Blue),
    TagChipData(Icons.Default.Save, Color.Red),
    TagChipData(Icons.Default.Satellite, Color.Green),
)

data class TagChipData(
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconTint: Color = Color.White
)