package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.SetFidRequest
import com.example.mobv.api.requests.UserCreateRequest
import com.google.firebase.auth.FirebaseAuth

class RegisterModel : UserAccessModel() {

    private val auth = FirebaseAuth.getInstance()


    suspend fun register(ctx: Context, email: String, password: String): LoggedUser {
        val zadanieApi = ZadanieApi.create(ctx)

        val userCreateRequest = UserCreateRequest(email, password)

        return convert(email, zadanieApi.createUser(userCreateRequest))
    }

    suspend fun setFID(ctx: Context, uid: String, fid: String) {
        val zadanieApi = ZadanieApi.create(ctx)

        val setFidRequest = SetFidRequest(uid, fid)
        zadanieApi.setFID(setFidRequest)
    }

}