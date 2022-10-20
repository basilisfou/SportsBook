package com.vasilis.eventbook.di

import com.vasilis.eventbook.managers.TimeUseCase
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
object ManagersModule {

    @Singleton
    @Provides
    fun provideTimeManagerUseCase() = TimeUseCase()

}