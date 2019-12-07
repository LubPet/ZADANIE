package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.Room
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
    fun listRooms(@Body body: RoomListRequest): Call<List<Room>>

    @POST("room/message.php")
    @Headers("ZadanieApiAuth: accept")
    fun messageRoom(@Body body: MessageRoomRequest): Call<Any>

    @POST("room/read.php")
    @Headers("ZadanieApiAuth: accept")
    fun readRoom(@Body body: ReadRoomRequest): Call<List<RoomMessage>>

    @POST("contact/list.php")
    @Headers("ZadanieApiAuth: accept")
    fun listContacts(@Body body: ListContactRequest): Call<List<Contact>>

    @POST("contact/message.php")
    @Headers("ZadanieApiAuth: accept")
    fun messageContact(@Body body: MessageContactRequest): Call<Any>

    @POST("contact/read.php")
    @Headers("ZadanieApiAuth: accept")
    fun readContact(@Body body: ReadContactRequest): Call<List<ContactMessage>>

    @POST("user/refresh.php")
    fun tokenRefreshCall(@Body body: RefreshTokenRequest): Call<LoggedUser>

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