package com.vasilis.eventbook.di

import com.vasilis.data.network.EventBookApi
import com.vasilis.data.network.builders.RetrofitBuilder
import com.vasilis.eventbook.BuildConfig
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): EventBookApi {
        return RetrofitBuilder().makeRetrofitService(
            baseUrl = BuildConfig.apiBaseEndpoint,
            isDebuggable = BuildConfig.DEBUG
        )
    }
}