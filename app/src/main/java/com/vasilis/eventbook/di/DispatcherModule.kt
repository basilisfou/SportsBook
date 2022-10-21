package com.vasilis.eventbook.di
import com.vasilis.eventbook.ui.coroutines.DispatcherProvider
import com.vasilis.eventbook.ui.coroutines.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

/**
 * Created by Vasilis Fouroulis on 20/10/2022.
 * vasilisfouroulis@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    fun provideDispatcherProvider() : DispatcherProvider {
        return DispatcherProviderImpl(
            mainThread = Dispatchers.Main,
            backgroundThread = Dispatchers.IO,
            default = Dispatchers.Default,
            unconfined = Dispatchers.Unconfined
        )
    }
}