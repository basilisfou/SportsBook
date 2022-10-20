package com.vasilis.domain.model

import java.util.*

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
data class Event(
    val id: Int = 0,
    val eventOpponent1 : String = "",
    val eventOpponent2 : String = "",
    val timeOfEvent : Date? = null,
    val sportCategory: String = "",
    var isFavorite: Boolean = false
)