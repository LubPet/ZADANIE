package com.example.mobv.api

import android.content.Context
import com.example.mobv.api.RoomListRequest
import com.example.mobv.api.UserLoginRequest
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ZadanieApi{

    @POST("http://zadanie.mpage.sk/user/login.php")
    suspend fun userLogin(@Body body: UserLoginRequest): Response

    @POST("http://zadanie.mpage.sk/room/list.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun roomList(@Body body: RoomListRequest): Response

    @POST("http://zadanie.mpage.sk/user/refresh.php")
    fun tokenRefreshCall(@Body body: RefreshTokenRequest): Call


    companion object {

        fun create(context: Context): ZadanieApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://zadanie.mpage.sk/user/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ZadanieApi::class.java)
        }
    }
}