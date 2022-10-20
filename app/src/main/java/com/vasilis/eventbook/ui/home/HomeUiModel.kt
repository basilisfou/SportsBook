package com.vasilis.eventbook.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Created by Vasilis Fouroulis on 19/10/22.
 * vasilisfouroulis@gmail.com
 */
data class SportUiModel(
    val sportName: String = "",
    val categoryName: String = "",
    val events: MutableState<List<EventUiModel>>
)

data class EventUiModel(
    val id: Int = 0,
    val eventOpponent1: String = "",
    val eventOpponent2: String = "",
    val sportCategory: String = "",
    var isFavorite: MutableState<Boolean> = mutableStateOf(false)
) {
    val timeOfEvent: MutableState<String> = mutableStateOf("")
}