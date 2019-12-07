package com.example.mobv.api.responses

import com.example.mobv.utils.DateUtils
import java.util.*

class RoomMessage(val uid: String, val roomid: String, val message: String, private val time: String, val name: String) {

    fun getTime(): Date {
        return DateUtils.parseTime(time)!!
    }
}
