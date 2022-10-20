package com.vasilis.eventbook.ui.home

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.vasilis.eventbook.managers.TimeUseCase
import java.util.*

/**
 * Created by Vasilis Fouroulis on 20/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun Timer(
    eventTime : Date,
    changeTimeDelta: (Long?) -> Unit
) {
    val countDownTimer = object : CountDownTimer(TimeUseCase().timeDeltaTillFinish(eventTime) ?: 0L, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            changeTimeDelta.invoke(
                millisUntilFinished
            )
        }

        override fun onFinish() {
            changeTimeDelta.invoke(null)
        }
    }


    DisposableEffect(key1 = "key") {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
}
