package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.RoomListRequest

class RoomRepository {

    suspend fun getRoomList(ctx: Context, uid: String): List<Room> {
        val zadanieApi = ZadanieApi.create(ctx)

        val roomListRequest = RoomListRequest(uid)
        return zadanieApi.listRoom(roomListRequest)
    }

}