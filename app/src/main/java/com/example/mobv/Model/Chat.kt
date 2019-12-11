package com.example.mobv.Model

import java.util.*

class Chat {
    var senderName: String? = null
    var sender: String? = null
    var receiver: String? = null
    var message: String? = null
    var time: Date? = null

    constructor(sender: String?, receiver: String?, message: String?) {
        this.sender = sender
        this.receiver = receiver
        this.message = message
    }

    constructor() {}

}