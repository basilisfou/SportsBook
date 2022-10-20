package com.vasilis.eventbook.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.vasilis.eventbook.ui.commonUi.ShimmerAnimation
import com.vasilis.eventbook.ui.event.EventItem
import com.vasilis.eventbook.ui.sport.SportItem
import com.vasilis.eventbook.ui.theme.Primary

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
            ) {  event ->
                viewModel.setFavorite(
                    event = event
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    data: EventsBySport,
    onClickFavorite:  ( event : EventUiModel) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        items(data.keys.size){ indexSport ->
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
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Primary)
                ) {
                    data[sport]?.let {
                        items(it.size) { index ->
                            EventItem(
                                event = it[index],
                                onClickFavorite = onClickFavorite
                            )
                        }
                    }

                }
            }
        }
    })
}