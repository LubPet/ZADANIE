package com.example.mobv.Model

import com.google.gson.Gson
import okhttp3.ResponseBody

abstract class UserAccessModel {

    fun convert(email: String, body: ResponseBody): LoggedUser {
        val user = Gson().fromJson(body.string(), LoggedUser::class.java)
        user.email = email

        return user
    }

}