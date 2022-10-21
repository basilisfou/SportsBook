package com.vasilis.eventbook.ui.sport

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vasilis.eventbook.ui.theme.Secondary
import com.vasilis.eventbook.ui.theme.SportTextStyle
import com.vasilis.eventbook.ui.commonUi.ClearRippleTheme
import com.vasilis.eventbook.ui.uiModel.SportUiModel

/**
 * Created by Vasilis Fouroulis on 19/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun SportItem(
    sport: SportUiModel,
    expand : Boolean,
    onExpandListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Secondary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = sport.sportName,
            style = SportTextStyle
        )

        val angle: Float by animateFloatAsState(
            targetValue = if (expand) {
                180f
            } else {
                0.0f
            },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )

        CompositionLocalProvider(
            LocalRippleTheme provides ClearRippleTheme
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 5.dp)
                    .rotate(angle)
                    .clickable {
                        onExpandListener.invoke()
                    },
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Favorite",
                tint = Color.White
            )
        }
    }

}