package com.vasilis.eventbook.di

import com.vasilis.data.network.EventBookApi
import com.vasilis.data.repository.SportsRepositoryImpl
import com.vasilis.domain.repository.SportsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSportsRepository(api: EventBookApi) : SportsRepository = SportsRepositoryImpl(api)
}