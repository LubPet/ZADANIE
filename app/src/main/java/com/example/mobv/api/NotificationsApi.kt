package com.example.mobv.api

import android.content.Context
import com.example.mobv.api.requests.*
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface NotificationsApi {

    @POST("fcm/send")
    fun notifyContact(@Body body: NotifyContactRequest): Call<Void>

    companion object {

        private const val BASE_URL = "https://fcm.googleapis.com/"

        fun create(context: Context): NotificationsApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(NotificationInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NotificationsApi::class.java)
        }
    }
}