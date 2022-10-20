package com.vasilis.eventbook.ui.event

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vasilis.eventbook.ui.commonUi.ClearRippleTheme
import com.vasilis.eventbook.ui.home.EventUiModel
import com.vasilis.eventbook.ui.home.Timer
import com.vasilis.eventbook.ui.theme.EventTextStyle
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun EventItem(
    event: EventUiModel,
    onClickFavorite: (event: EventUiModel) -> Unit
) {

    var time by rememberSaveable {  mutableStateOf(0L) }
    Timer(
        eventTime = event.timeOfEvent,
        changeTimeDelta = {
            time = it ?: 0
        }
    )

    Column(
        modifier = Modifier
            .height(120.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        TimeView(
            time = getCountdownText(time)
        )

        FavoriteView(
            isFavorite = event.isFavorite.value,
            onClick = {
                onClickFavorite.invoke(event)
            }
        )

        EventOpponents(
            event.eventOpponent1,
            event.eventOpponent2
        )
    }
}

@Composable
fun TimeView(time: String) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = Color.White
            )
            .padding(4.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(fraction = 0.8f),
            text = time,
            style = EventTextStyle,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoriteView(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalRippleTheme provides ClearRippleTheme
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onClick.invoke()
                },
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorite",
            tint = if (isFavorite) {
                Color.Red
            } else {
                Color.White
            }
        )
    }
}

@Composable
fun EventOpponents(
    opponent1: String,
    opponent2: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = opponent1,
            style = EventTextStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(
            modifier = Modifier.height(1.dp)
        )

        Text(
            text = opponent2,
            style = EventTextStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

private fun getCountdownText(timeDelta: Long): String {
    return when {
        TimeUnit.MILLISECONDS.toDays(timeDelta) < 1 && timeDelta != 0L -> {
            String.format(
                Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeDelta),
                TimeUnit.MILLISECONDS.toMinutes(timeDelta) - TimeUnit.HOURS.toMinutes(
                    TimeUnit.MILLISECONDS.toHours(
                        timeDelta
                    )
                ),
                TimeUnit.MILLISECONDS.toSeconds(timeDelta) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        timeDelta
                    )
                )
            )
        }
        timeDelta == 0L -> {
            "Started"
        }
        else -> {
            String.format(
                Locale.getDefault(), "%dd %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toDays(timeDelta),
                TimeUnit.MILLISECONDS.toHours(timeDelta) - TimeUnit.DAYS.toHours(
                    TimeUnit.MILLISECONDS.toDays(
                        timeDelta
                    )
                ),
                TimeUnit.MILLISECONDS.toMinutes(timeDelta) - TimeUnit.HOURS.toMinutes(
                    TimeUnit.MILLISECONDS.toHours(
                        timeDelta
                    )
                ),
                TimeUnit.MILLISECONDS.toSeconds(timeDelta) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        timeDelta
                    )
                )
            )
        }
    }
}