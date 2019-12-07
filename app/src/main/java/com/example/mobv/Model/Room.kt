package com.example.mobv.Model

import com.example.mobv.utils.DateUtils
import java.util.*

class Room(val roomid: String, private val time: String) {

    fun getTime(): Date {
        return DateUtils.parseTime(time)!!
    }

}
