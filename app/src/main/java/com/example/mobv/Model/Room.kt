package com.example.mobv.Model

import com.example.mobv.utils.DateUtils
import java.util.*

class Room(val roomid: String, private val time: String) : ChatRoom {

    override fun getName(): String {
        return roomid
    }

    override fun getTime(): Date {
        return DateUtils.parseTime(time)!!
    }

    companion object {

        private const val PUBLIC_ROOM_ID: String = "XsTDHS3C2YneVmEW5Ry7"

        fun public(): Room {
            return Room(PUBLIC_ROOM_ID, "2000-01-01 00:00:00")
        }

    }

}
