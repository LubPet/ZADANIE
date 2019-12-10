package com.example.mobv.Model

import java.io.Serializable
import java.util.*

interface ChatRoom : Serializable {

    fun getName(): String

    fun getTime(): Date?

}