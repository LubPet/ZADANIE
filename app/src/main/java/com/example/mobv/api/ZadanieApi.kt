package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.Room
import com.example.mobv.api.requests.*
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage
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
    suspend fun userLogin(@Body body: UserLoginRequest): LoggedUser

    @POST("user/create.php")
    suspend fun createUser(@Body body: UserCreateRequest): LoggedUser

    @POST("user/fid.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun setFID(@Body body: SetFidRequest): ResponseBody

    @POST("room/list.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun listRoom(@Body body: RoomListRequest): List<Room>

    @POST("room/message.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun messageRoom(@Body body: MessageRoomRequest): ResponseBody

    @POST("room/read.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun readRoom(@Body body: ReadRoomRequest): List<RoomMessage>

    @POST("contact/list.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun listContact(@Body body: ListContactRequest): List<Contact>

    @POST("contact/message.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun messageContact(@Body body: MessageContactRequest): ResponseBody

    @POST("contact/read.php")
    @Headers("ZadanieApiAuth: accept")
    suspend fun readContact(@Body body: ReadContactRequest): List<ContactMessage>

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