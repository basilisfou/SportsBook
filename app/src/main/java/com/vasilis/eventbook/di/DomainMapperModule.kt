package com.vasilis.eventbook.di

import com.vasilis.domain.mappers.EventMapper
import com.vasilis.domain.mappers.EventMapperImpl
import com.vasilis.domain.mappers.SportMapper
import com.vasilis.domain.mappers.SportMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainMapperModule {

    @Provides
    fun provideSportsMapper(eventMapper: EventMapper) : SportMapper = SportMapperImpl(eventMapper)

    @Provides
    fun provideEventMapper() : EventMapper = EventMapperImpl()
}