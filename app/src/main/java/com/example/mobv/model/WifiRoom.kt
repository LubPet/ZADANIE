package com.example.mobv.model

import java.util.*


class WifiRoom(private val name: String) : ChatRoom {

    override fun getName(): String {
        return name
    }

    override fun getTime(): Date? {
        return Date()
    }


}
