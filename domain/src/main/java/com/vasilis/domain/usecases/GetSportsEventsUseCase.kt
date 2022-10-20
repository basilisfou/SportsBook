package com.vasilis.domain.usecases

import com.vasilis.domain.DomainResult
import com.vasilis.domain.mappers.SportMapper
import com.vasilis.domain.model.Sport
import com.vasilis.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmai.com
 */
class GetSportsEventsUseCase(
    private val repository: SportsRepository,
    private val mapper: SportMapper
) {

    operator fun invoke(): Flow<DomainResult<List<Sport>>> = flow {
        repository
            .getSports()
            .onSuccess {
                emit(
                    DomainResult.Success(
                        mapper.mapResponseToDomain(it)
                    )
                )
            }.onFailure {
                it.printStackTrace()
                emit(
                    DomainResult.Error(
                        error = it.toString()
                    )
                )
            }
    }
}