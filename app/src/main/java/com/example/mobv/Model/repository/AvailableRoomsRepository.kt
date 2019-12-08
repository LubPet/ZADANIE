package com.example.mobv.Model.repository

import android.content.Context
import com.example.mobv.Model.WifiRoom
import com.example.mobv.wifi.repository.WifiRepository
import java.util.*

class AvailableRoomsRepository(private val ctx: Context, private val wifiRepository: WifiRepository) {

    fun getAvailableRooms(onSuccess: (List<WifiRoom>) -> Unit, onFailure: () -> Unit) {
        wifiRepository.performScan({ wifis ->
            val rooms = LinkedList<WifiRoom>()
            wifis.forEach {
                val name = it.SSID ?: it.BSSID
                rooms.add(WifiRoom(name!!))
            }
            onSuccess(rooms)
        }, {
            onFailure()
        })
    }

    companion object {

        fun create(ctx: Context): AvailableRoomsRepository {
            return create(ctx, WifiRepository(ctx))
        }

        fun create(ctx: Context, repo: WifiRepository): AvailableRoomsRepository {
            return AvailableRoomsRepository(ctx, repo)
        }
    }
}