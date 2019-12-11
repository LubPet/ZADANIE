package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.LoggedUser
import com.example.mobv.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class NotificationInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val loggedUser = SessionManager.get(context).getSessionData()!!
        val accessToken = "AIzaSyBCH9z9pgtfCk8drj1wk3yrg2kshrtTD4c"
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=$accessToken")

        return chain.proceed(request.build())
    }
}