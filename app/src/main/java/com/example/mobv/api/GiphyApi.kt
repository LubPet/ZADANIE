package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.GifResource
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.Room
import com.example.mobv.api.requests.*
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.GiphyResponseData
import com.example.mobv.api.responses.RoomMessage
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface GiphyApi {
    
    @GET("v1/gifs/search")
    fun search(@Query("api_key") apiKey: String, @Query("q") query: String, @Query("limit") limit: Int): Call<GiphyResponseData>

    companion object {

        private const val BASE_URL = "https://api.giphy.com"

        const val API_KEY = "npM0uGSAireZWYSH4jDW7jGf5nyk7WhR"

        fun create(context: Context): GiphyApi {

            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GiphyApi::class.java)
        }
    }
}