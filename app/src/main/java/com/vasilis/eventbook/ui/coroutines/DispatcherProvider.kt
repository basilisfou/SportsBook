package com.vasilis.eventbook.ui.coroutines

import kotlinx.coroutines.CoroutineDispatcher
/**
 * Created by Vasilis Fouroulis on 20/10/2022.
 * vasilisfouroulis@gmail.com
 */
interface DispatcherProvider{
    fun getMainThread(): CoroutineDispatcher
    fun getBackgroundThread(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
    fun getUnconfined(): CoroutineDispatcher
}

class DispatcherProviderImpl(
    private val mainThread: CoroutineDispatcher,
    private val backgroundThread: CoroutineDispatcher,
    private val default: CoroutineDispatcher,
    private val unconfined: CoroutineDispatcher
): DispatcherProvider {

    override fun getMainThread(): CoroutineDispatcher {
        return mainThread
    }

    override fun getBackgroundThread(): CoroutineDispatcher {
        return backgroundThread
    }

    override fun getDefault(): CoroutineDispatcher {
        return default
    }

    override fun getUnconfined(): CoroutineDispatcher{
        return unconfined
    }

}