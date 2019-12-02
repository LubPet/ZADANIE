package com.example.mobv.api.requests

class UserLoginRequest(private val name: String, private val password: String) : ZadanieRequest() {

    fun getName(): String {
        return this.name
    }

    fun getPassword(): String {
        return this.password
    }

    override fun toString(): String {
        return "UserLoginResponse(name='$name', password='$password')"
    }

}