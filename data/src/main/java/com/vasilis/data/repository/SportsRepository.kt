package com.vasilis.data.repository

import com.vasilis.data.network.EventBookApi
import com.vasilis.domain.repository.SportsRepository
import com.vasilis.domain.response.SportResponse

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
class SportsRepositoryImpl (
    private val api: EventBookApi
) : SportsRepository{

    override suspend fun getSports(): Result<List<SportResponse>> {
        return kotlin.runCatching { api.getSports() }
    }
}