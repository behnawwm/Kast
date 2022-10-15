package com.example.kast.android.ui.watchlist

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.presentation.viewModel.WatchlistViewModel
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WatchlistScreen() {
    val viewModel = getViewModel<WatchlistViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = { WatchListTopBar() }) { paddingValues ->


        val pagerState = rememberPagerState()

        val watchlistPagerState = rememberPagerState()
        val watchlistTabIndex = watchlistPagerState.currentPage

        val historyPagerState = rememberPagerState()
        val historyTabIndex = historyPagerState.currentPage

        val coroutineScope = rememberCoroutineScope()

        val watchListTabPosition = 0
        val historyTabPosition = 1

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .alpha(
                            if (pagerState.currentPage == watchListTabPosition) {
                                1f
                            } else {
                                0.5f
                            }
                        )
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(watchListTabPosition)
                            }
                        },
                    text = "Watchlist",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .alpha(
                            if (pagerState.currentPage == historyTabPosition) {
                                1f
                            } else {
                                0.5f
                            }
                        )
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(historyTabPosition)
                            }
                        },
                    text = "History",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            HorizontalPager(count = 2, state = pagerState, modifier = Modifier) { currentPage ->
                if (currentPage == watchListTabPosition) {
                    // Watchlist TAB
                    Column {

                        TabLayout(
                            tabData = state.watchlistTabData,
                            tabIndex = watchlistTabIndex,
                            pagerState = watchlistPagerState,
                            coroutineScope = coroutineScope
                        )

                        HorizontalPager(
                            count = state.watchlistTabData.size,
                            state = watchlistPagerState,
                            modifier = Modifier
                        ) { currentPage ->
                            when (currentPage) {
                                0 -> {
                                    if (viewModel.state.value.databaseError == null)
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(viewModel.state.value.allMovies.filter { it.isBookmarked }) {
                                                MovieExtendedCard(
                                                    movie = it,
                                                    onMovieClick = {},
                                                    onOptionsClick = {},
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                                Divider(color = bottomNavigationContainerColor)
                                            }
                                        }
                                    else
                                        Text(text = viewModel.state.value.databaseError!!,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(16.dp)
                                        )
                                }
                                1 -> {
                                    if (viewModel.state.value.databaseError == null)
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(viewModel.state.value.allMovies.filter { it.isWatched }) {
                                                MovieExtendedCard(
                                                    movie = it,
                                                    onMovieClick = {},
                                                    onOptionsClick = {},
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                                Divider(color = bottomNavigationContainerColor)
                                            }
                                        }
                                    else
                                        Text(text = viewModel.state.value.databaseError!!,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(16.dp)
                                        )
                                }
                                2 -> {
                                    if (viewModel.state.value.databaseError == null)
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(viewModel.state.value.allMovies.filter { it.isCollected }) {
                                                MovieExtendedCard(
                                                    movie = it,
                                                    onMovieClick = {},
                                                    onOptionsClick = {},
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                                Divider(color = bottomNavigationContainerColor)
                                            }
                                        }
                                    else
                                        Text(text = viewModel.state.value.databaseError!!,
                                            color = Color.White,
                                            modifier = Modifier
                                                .padding(16.dp)
                                        )
                                }

                            }

                        }
                    }
                } else {
                    // History TAB
                    Column {
                        TabLayout(
                            tabData = state.historyTabData,
                            tabIndex = historyTabIndex,
                            pagerState = historyPagerState,
                            coroutineScope = coroutineScope
                        )

                        HorizontalPager(
                            count = state.historyTabData.size,
                            state = historyPagerState,
                            modifier = Modifier
                        ) { currentPage ->
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = "Not Implemented",
                                    style = MaterialTheme.typography.bodySmall
                                )

                            }

                        }
                    }

                }
            }
        }


    }
}


