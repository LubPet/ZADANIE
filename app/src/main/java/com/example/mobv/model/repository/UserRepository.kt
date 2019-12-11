package com.example.mobv.model.repository

import android.content.Context
import com.example.mobv.model.LoggedUser
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.SetFidRequest
import com.example.mobv.api.requests.UserCreateRequest
import com.example.mobv.api.requests.UserLoginRequest
import com.example.mobv.session.SessionManager

class UserRepository {

    suspend fun login(ctx: Context, username: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userLoginRequest = UserLoginRequest(username, password)

        val loggedUser = zadanieApi.userLogin(userLoginRequest)
        SessionManager.get(ctx).saveSessionData(loggedUser)

        return loggedUser
    }

    suspend fun register(ctx: Context, username: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userCreateRequest = UserCreateRequest(username, password)

        return zadanieApi.createUser(userCreateRequest)
    }

    suspend fun setFID(ctx: Context, uid: String, fid: String) {
        val zadanieApi = ZadanieApi.create(ctx)

        val setFidRequest = SetFidRequest(uid, fid)
        zadanieApi.setFID(setFidRequest)
    }

    fun logout(ctx: Context) {
        SessionManager.get(ctx).clearSessionData()
    }

}