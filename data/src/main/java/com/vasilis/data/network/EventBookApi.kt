package com.vasilis.data.network

import com.vasilis.domain.response.SportResponse
import retrofit2.http.GET

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
interface EventBookApi {

    @GET("api/sports")
    suspend fun getSports(): List<SportResponse>
}