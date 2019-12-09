package com.example.mobv.Model

import java.util.*


class WifiRoom(private val name: String) : ChatRoom {

    override fun getName(): String {
        return name
    }

    override fun getTime(): Date? {
        return Date()
    }


}
