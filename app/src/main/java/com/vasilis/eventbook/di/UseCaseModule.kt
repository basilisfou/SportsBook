package com.vasilis.eventbook.di

import com.vasilis.domain.mappers.SportMapper
import com.vasilis.domain.repository.SportsRepository
import com.vasilis.domain.usecases.GetSportsEventsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetSportEventsUseCase(
        sportRepository: SportsRepository,
        mapper: SportMapper
    ) = GetSportsEventsUseCase(
        repository = sportRepository,
        mapper = mapper
    )
}