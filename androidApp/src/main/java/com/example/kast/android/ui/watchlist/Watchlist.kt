package com.example.kast.android.ui.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.theme.bodyColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview
@Composable
@Preview
fun WatchlistScreen() {
    Scaffold(topBar = { WatchListTopBar() }) { paddingValues ->
        val items = createItems()
        val pagerState = rememberPagerState()

        val watchlistPagerState = rememberPagerState()
        val watchlistTabIndex = watchlistPagerState.currentPage

        val historyPagerState = rememberPagerState()
        val historyTabIndex = historyPagerState.currentPage

        val coroutineScope = rememberCoroutineScope()
        val watchlistTabData = listOf(
            "Movies", "TV Shows", "Seasons", "Episodes"
        )
        val historyTabData = listOf(
            "Movies", "TV Shows", "Episodes"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    text = "Watchlist",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Text(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    text = "History",
                    style = MaterialTheme.typography.headlineLarge
                )
            }



            HorizontalPager(count = 2, state = pagerState, modifier = Modifier) { currentPage ->
                if (currentPage == 0) {
                    // Watchlist TAB
                    TabRow(
                        selectedTabIndex = watchlistTabIndex,
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        watchlistTabData.forEachIndexed { index, text ->
                            Tab(selected = watchlistTabIndex == index,
                                onClick = {
                                coroutineScope.launch {
                                    watchlistPagerState.scrollToPage(index)
                                }
                            }, text = {
                                Text(text = text)
                            })
                        }
                    }
                } else {
                    // History TAB
                    TabRow(
                        selectedTabIndex = historyTabIndex,
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        historyTabData.forEachIndexed { index, text ->
                            Tab(selected = historyTabIndex == index, onClick = {
                                coroutineScope.launch {
                                    historyPagerState.animateScrollToPage(index)
                                }
                            }, text = {
                                Text(text = text)
                            })
                        }
                    }
                }
            }

            HorizontalPager(
                count = 4, state = watchlistPagerState, modifier = Modifier
            ) { currentPage ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = items[currentPage].title, fontSize = 16.sp
                    )

                }

            }
        }

    }
}

fun createItems() = listOf(
    HorizontalPagerContent(title = "Movie"),
    HorizontalPagerContent(title = "Tv Show"),
    HorizontalPagerContent(title = "Season"),
    HorizontalPagerContent(title = "Episodes"),
)

data class HorizontalPagerContent(
    val title: String,
)