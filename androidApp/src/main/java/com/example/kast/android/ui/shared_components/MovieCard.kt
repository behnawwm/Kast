package com.example.kast.android.ui.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.theme.black50Alpha
import com.example.kast.android.theme.bodyColor
import com.example.kast.android.utils.AsyncImage
import com.example.kast.android.utils.addEmptyLines
import com.example.kast.data.model.MovieView
import kotlinx.coroutines.launch


@Composable
fun MovieCard(
    movie: MovieView,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit
) {

    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .width(140.dp)
        .clickable {
            onMovieClick(movie)
        }
    ) {

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
                    onOptionsClick(movie)
                }
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    "",
                    tint = bodyColor,
                )
            }
        }

    }

}