package com.example.mobv.Model.repository

import android.content.Context
import com.example.mobv.Model.WifiRoom
import java.util.*

class AvailableRoomsRepository(private val ctx: Context, private val wifiRepository: WifiRepository) {

    fun getAvailableRooms(): List<WifiRoom> {
        val wifis = wifiRepository.getConnectionInfo()
        val rooms = LinkedList<WifiRoom>()

        wifis.forEach {
            if (it.SSID != null && it.SSID.isNotEmpty() && it.SSID != "<unknown ssid>") {
                rooms.add(WifiRoom(it.SSID))
            } else {
                rooms.add(WifiRoom(it.BSSID!!))
            }
        }

        return rooms
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