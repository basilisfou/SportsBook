package com.vasilis.eventbook.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.vasilis.eventbook.ui.commonUi.ShimmerAnimation
import com.vasilis.eventbook.ui.event.EventItem
import com.vasilis.eventbook.ui.sport.SportItem
import com.vasilis.eventbook.ui.theme.Primary
import kotlinx.coroutines.launch

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {

    val events by viewModel.sportEvents
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading() -> ShimmerAnimation()
        uiState.isSuccess() -> {
            HomeScreenContent(
                data = events
            ) { sport, event ->
                viewModel.setFavorite(
                    sport = sport,
                    eventId = event.id
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    data: EventsBySport,
    onClickFavorite: (sport: SportUiModel, event: EventUiModel) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        items(data.keys.size) { indexSport ->
            val sport = data.keys.toList()[indexSport]
            var expand by rememberSaveable {
                mutableStateOf(true)
            }

            SportItem(
                sport = sport,
                expand = expand,
                onExpandListener = {
                    expand = !expand
                }
            )

            AnimatedVisibility(
                visible = expand,
                modifier = Modifier
                    .fillMaxWidth(),
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 500),
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 500),
                )
            ) {
                val listState = rememberLazyListState()

                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Primary)
                ) {
                    data[sport]?.let {
                        items(
                            count = it.size,
                            key = { index ->
                                it[index].id
                            }
                        ) { index ->
                            EventItem(
                                event = it[index],
                                onClickFavorite = { event ->
                                    onClickFavorite.invoke(
                                        sport,
                                        event
                                    )
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    })
}