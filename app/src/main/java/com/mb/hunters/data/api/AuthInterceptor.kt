package com.mb.hunters.data.api

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build()
        return chain.proceed(request)
    }
}