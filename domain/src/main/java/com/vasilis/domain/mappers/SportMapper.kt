package com.vasilis.domain.mappers

import com.vasilis.domain.response.SportResponse
import com.vasilis.domain.model.Sport

/**
 * Created by Vasilis Fouroulis on 17/10/22.
 * vasilisfouroulis@gmail.com
 */
interface SportMapper {
    fun mapResponseToDomain(sportResponse: List<SportResponse?>?): List<Sport>
}

class SportMapperImpl(
    private val eventMapper: EventMapper
) : SportMapper{

    override fun mapResponseToDomain(sportResponse: List<SportResponse?>?): List<Sport> {
        return sportResponse?.map {
            Sport(
                sportName = it?.d ?: "",
                events = eventMapper.mapResponseToDomain(it?.e),
                categoryName = it?.i?: ""
            )
        } ?: arrayListOf()
    }
}