package com.vasilis.domain.repository

import com.vasilis.domain.response.SportResponse

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
interface SportsRepository {
    suspend fun getSports(): Result<List<SportResponse>>
}