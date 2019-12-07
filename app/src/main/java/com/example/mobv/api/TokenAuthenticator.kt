package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.LoggedUser
import com.example.mobv.api.requests.RefreshTokenRequest
import com.example.mobv.api.responses.RefreshTokenResponse
import com.example.mobv.session.SessionManager
import com.google.gson.Gson
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.Authenticator
import kotlin.math.log

class TokenAuthenticator(val context: Context) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val loggedUser = SessionManager.get(context).getSessionData()
        var userAccessToken = loggedUser!!.access
        val userId = loggedUser.uid

        if (response.request.header("ZadanieApiAuth")?.compareTo("accept") == 0 && response.code == 401) {

            if (!response.request.header("Authorization").equals("Bearer $userAccessToken")) {
                return null
            }

            val refreshToken = loggedUser.refresh!!

            val tokenResponse = ZadanieApi.create(context)
                .tokenRefreshCall(RefreshTokenRequest(userId, refreshToken)).execute()

            if (tokenResponse.isSuccessful) {
                val refreshResponse = tokenResponse.body()!!
                userAccessToken = refreshResponse.access

                SessionManager.get(context).saveSessionData(loggedUser)

                return response.request.newBuilder()
                    .header("Authorization", "Bearer $userAccessToken")
                    .build()
            }

        }
        return null
    }
}