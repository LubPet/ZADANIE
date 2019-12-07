package com.example.mobv.Model

class LoggedUser {

    var uid: String

    var fid: String = ""

    var username: String

    var access: String? = null

    var refresh: String? = null

    constructor() : this("", "")

    constructor(uid: String, username: String) {
        this.uid = uid
        this.username = username
    }

    constructor(uid: String, email: String, username: String, access: String, refresh: String) {
        this.uid = uid
        this.username = username
        this.access = access
        this.refresh = refresh
    }

}
