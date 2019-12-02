package com.example.mobv.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent", "Zadanie-Android/1.0.0")
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")

        if (chain.request().header("ZadanieApiAuth")?.compareTo("accept") == 0) {
            val accessToken = "" // TODO get from session somehow
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}