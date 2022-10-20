package com.vasilis.data.network.builders
import com.vasilis.data.network.EventBookApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
class RetrofitBuilder {
    fun makeRetrofitService(
        baseUrl: String,
        isDebuggable: Boolean,
    ): EventBookApi {

        val okHttpClient = makeOkHttpBuilder(
            isDebuggable = isDebuggable
        )

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventBookApi::class.java)
    }

    private fun makeOkHttpBuilder(
        isDebuggable: Boolean
    ): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptorBuilder().makeHttpLoggingInterceptor(isDebuggable)
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(httpLoggingInterceptor)
        return builder.build()
    }


}