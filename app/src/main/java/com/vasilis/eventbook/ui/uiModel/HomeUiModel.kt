package com.vasilis.eventbook.ui.uiModel

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
    val sortNumber: Int = 0,
    val eventOpponent1: String = "",
    val eventOpponent2: String = "",
    val sportCategory: String = "",
    val timeOfEvent: Date? = null,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
) : Comparable<EventUiModel>{
    override fun equals(other: Any?): Boolean {
        return when{
            other !is EventUiModel -> false
            this.id == other.id -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + eventOpponent1.hashCode()
        result = 31 * result + eventOpponent2.hashCode()
        result = 31 * result + sportCategory.hashCode()
        result = 31 * result + (timeOfEvent?.hashCode() ?: 0)
        result = 31 * result + isFavorite.hashCode()
        return result
    }

    override fun compareTo(other: EventUiModel): Int {
        return compareValuesBy(this,other, { it.isFavorite.value.not() }, { it.sortNumber })
    }
}