package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.UserLoginRequest

class LoginModel : UserAccessModel() {

    suspend fun login(ctx: Context, username: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userLoginRequest = UserLoginRequest(username, password)
        val user = convert(username, zadanieApi.userLogin(userLoginRequest))

        createSession(user)
        return user
    }

}