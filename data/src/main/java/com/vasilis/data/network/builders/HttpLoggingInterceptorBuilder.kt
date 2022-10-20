package com.vasilis.data.network.builders

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
class HttpLoggingInterceptorBuilder {

    fun makeHttpLoggingInterceptor(isDebuggable: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (isDebuggable) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}