package com.example.kast.android.ui.watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    tabData: List<String>,
    tabIndex: Int,
    pagerState: PagerState,
    coroutineScope : CoroutineScope
) {
    TabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier.padding(top = 20.dp),
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(selected = tabIndex == index, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                Text(text = text, style = MaterialTheme.typography.bodySmall)
            })
        }
    }
}


data class HorizontalPagerContent(
    val title: String,
)