package com.example.mobv.api.responses

data class UserLoginResponse(private val uid: String, private val access: String, private val refresh: String)