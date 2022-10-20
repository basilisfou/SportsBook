package com.vasilis.domain.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
data class EventResponse(
    @SerializedName("d")
    val d : String?,
    @SerializedName("i")
    val i : String?,
    @SerializedName("si")
    val si : String?,
    @SerializedName("sh")
    val sh : String?,
    @SerializedName("tt")
    val tt : Long?
)