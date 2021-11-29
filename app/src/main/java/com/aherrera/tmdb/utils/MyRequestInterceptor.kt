package com.aherrera.tmdb.utils

import com.aherrera.tmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MyRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.api_key_tmdb)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
