package com.example.mobv.api.requests

data class RefreshTokenRequest(val uid: String, val refresh: String) : ZadanieRequest()
