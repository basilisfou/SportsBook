package com.vasilis.eventbook.managers

import java.util.*

/**
 * Created by Vasilis Fouroulis on 20/10/22.
 * vasilisfouroulis@gmail.com
 */
class TimeUseCase(
    private val now: Date = Date()
) {
    fun timeDeltaTillFinish(timeOfEvent: Date?): Long? {
        return timeOfEvent
            ?.takeIf { now.before(it) }
            ?.let { timeOfEvent.time - now.time }
    }
}