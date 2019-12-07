package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.UserLoginRequest
import com.example.mobv.session.SessionManager

class LoginModel : UserAccessModel() {

    suspend fun login(ctx: Context, email: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userLoginRequest = UserLoginRequest(email, password)

        val loggedUser = convert(email, zadanieApi.userLogin(userLoginRequest))
        SessionManager.get(ctx).saveSessionData(loggedUser)

        return loggedUser
    }

    fun logout(ctx: Context) {
        SessionManager.get(ctx).clearSessionData()
    }
}