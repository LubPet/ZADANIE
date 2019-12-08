package com.example.mobv.Model.repository

import android.content.Context
import com.example.mobv.Model.LoggedUser
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.RefreshTokenRequest
import retrofit2.Response

class TokenRepository {

    fun refresh(ctx: Context, loggedUser: LoggedUser): Response<LoggedUser> {
        val request = RefreshTokenRequest(loggedUser.uid, loggedUser.refresh!!)

        return ZadanieApi.create(ctx).tokenRefreshCall(request).execute()
    }

}