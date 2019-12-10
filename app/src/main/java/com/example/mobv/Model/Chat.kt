package com.example.mobv.Model

class Chat {
    var sender: String? = null
    var receiver: String? = null
    var message: String? = null

    constructor(sender: String?, receiver: String?, message: String?) {
        this.sender = sender
        this.receiver = receiver
        this.message = message
    }

    constructor() {}

}