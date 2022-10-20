package com.vasilis.domain.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
data class SportResponse(
    @SerializedName("i")
    val i : String?,
    @SerializedName("d")
    val d : String?,
    @SerializedName("e")
    val e : List<EventResponse?>?
)