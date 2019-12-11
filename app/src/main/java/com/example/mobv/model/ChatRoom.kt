package com.example.mobv.model

import java.io.Serializable
import java.util.*

interface ChatRoom : Serializable {

    fun getName(): String

    fun getTime(): Date?

}