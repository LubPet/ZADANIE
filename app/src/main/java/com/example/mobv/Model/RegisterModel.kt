package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.UserCreateRequest
import com.google.firebase.auth.FirebaseAuth

class RegisterModel : UserAccessModel() {

    private val auth = FirebaseAuth.getInstance()


    suspend fun register(ctx: Context, username: String, email: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userCreateRequest = UserCreateRequest(username, password)

        return convert(username, zadanieApi.createUser(userCreateRequest))
    }

}