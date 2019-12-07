package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.RoomListRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomRepository {

    suspend fun getRoomList(ctx: Context, uid: String): List<Room> {
        val zadanieApi = ZadanieApi.create(ctx)

        val roomListRequest = RoomListRequest(uid)
        val response = zadanieApi.roomList(roomListRequest)

        println("Room list response")
        println(response)

        val itemType = object : TypeToken<List<Room>>() {}.type
        return Gson().fromJson(response.string(), itemType)
    }

}