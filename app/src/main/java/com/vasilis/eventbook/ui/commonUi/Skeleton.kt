package com.vasilis.eventbook.ui.commonUi

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.vasilis.eventbook.ui.theme.ShimmerColorShades

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun ShimmerAnimation() {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerItem(
    brush: Brush
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        items(8) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(brush = brush)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = brush)
            ) {
                items(8) {
                    Spacer(
                        modifier = Modifier
                            .height(120.dp)
                            .width(100.dp)
                            .background(brush = brush)
                    )
                }
            }
        }
    })
}