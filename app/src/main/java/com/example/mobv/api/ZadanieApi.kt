package com.example.mobv.api

import android.content.Context
import com.example.mobv.api.requests.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ZadanieApi {

    @POST("user/login.php")
    suspend fun userLogin(@Body body: UserLoginRequest): ResponseBody

    @POST("user/create.php")
    suspend fun createUser(@Body body: UserCreateRequest): ResponseBody

    @POST("room/list.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun roomList(@Body body: RoomListRequest): ResponseBody

    @POST("user/refresh.php")
    fun tokenRefreshCall(@Body body: RefreshTokenRequest): Call

    companion object {

        private const val BASE_URL = "http://zadanie.mpage.sk/"

        fun create(context: Context): ZadanieApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ZadanieApi::class.java)
        }
    }
}