package com.vasilis.domain.model

import java.util.*

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
data class Event(
    val id: Int,
    val eventOpponent1 : String,
    val eventOpponent2 : String,
    val timeOfEvent : Date,
    val sportCategory: String,
    var isFavorite: Boolean = false
)