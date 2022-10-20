package com.vasilis.domain.mappers

import androidx.core.text.isDigitsOnly
import com.vasilis.domain.model.Event
import com.vasilis.domain.response.EventResponse
import java.util.*

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
interface EventMapper {
    fun mapResponseToDomain(eventResponse: List<EventResponse?>?): MutableList<Event>
}

class EventMapperImpl : EventMapper {
    override fun mapResponseToDomain(eventResponse: List<EventResponse?>?): MutableList<Event> {
        return eventResponse?.map { response ->
            val opponents = response?.d?.split("-")

            Event(
                id = response?.i?.takeIf { it -> it.isDigitsOnly() }?.toInt() ?: 0,
                eventOpponent1 = opponents?.get(0)?.trim() ?: "",
                eventOpponent2 = opponents?.get(1)?.trim() ?: "",
                timeOfEvent = response?.tt?.let { timeUnix -> parseEpochTimeToDate(timeUnix) } ?: Date(),
                sportCategory = response?.si ?: "",
                isFavorite = false
            )
        }?.toMutableList() ?: mutableListOf()
    }

    private fun parseEpochTimeToDate(timeUnix: Long): Date {
        return Date(timeUnix * 1000)
    }
}