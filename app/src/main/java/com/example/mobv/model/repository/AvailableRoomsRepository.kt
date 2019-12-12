package com.example.mobv.model.repository

import android.content.Context
import com.example.mobv.model.WifiRoom
import java.util.*

class AvailableRoomsRepository(private val ctx: Context, private val wifiRepository: WifiRepository) {

    fun getAvailableRooms(): List<WifiRoom> {
        val wifis = wifiRepository.getConnectionInfo()
        val rooms = LinkedList<WifiRoom>()

        wifis.forEach {
            if (it.SSID != null && it.SSID.isNotEmpty() && it.SSID != "<unknown ssid>") {
                rooms.add(WifiRoom(clean(it.SSID)))
            } else {
                rooms.add(WifiRoom(it.BSSID!!))
            }
        }

        return rooms
    }

    private fun clean(str: String): String {
        return str.replace("\"", "")
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