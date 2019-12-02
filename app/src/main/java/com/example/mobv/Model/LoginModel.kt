package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.UserLoginRequest

class LoginModel : UserAccessModel() {

    suspend fun login(ctx: Context, username: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userLoginRequest = UserLoginRequest(username, password)

        return convert(username, zadanieApi.userLogin(userLoginRequest))
    }

}