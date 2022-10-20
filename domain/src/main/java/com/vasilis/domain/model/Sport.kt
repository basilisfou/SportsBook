package com.vasilis.domain.model

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
data class Sport(
    val sportName : String,
    val categoryName : String,
    val events : MutableList<Event>
)