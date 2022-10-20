package com.vasilis.eventbook.ui.home
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

/**
 * Created by Vasilis Fouroulis on 19/10/22.
 * vasilisfouroulis@gmail.com
 */
data class SportUiModel(
    val sportName: String = "",
    val categoryName: String = "",
)

data class EventUiModel(
    val id: Int = 0,
    val eventOpponent1: String = "",
    val eventOpponent2: String = "",
    val sportCategory: String = "",
    val timeOfEvent: Date,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)