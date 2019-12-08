package com.example.mobv.api

import android.content.Context
import com.example.mobv.Model.repository.TokenRepository
import com.example.mobv.api.requests.RefreshTokenRequest
import com.example.mobv.session.SessionManager
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.Authenticator

class TokenAuthenticator(val context: Context) : Authenticator {

    private val sessionManager = SessionManager.get(context)

    override fun authenticate(route: Route?, response: Response): Request? {
        val loggedUser = sessionManager.getSessionData()
        var userAccessToken = loggedUser!!.access

        if (response.request.header("ZadanieApiAuth")?.compareTo("accept") == 0 && response.code == 401) {

            if (!response.request.header("Authorization").equals("Bearer $userAccessToken")) {
                return null
            }

            val tokenResponse = TokenRepository().refresh(context, loggedUser)

            if (tokenResponse.isSuccessful) {
                val refreshResponse = tokenResponse.body()!!
                userAccessToken = refreshResponse.access

                sessionManager.saveSessionData(loggedUser)

                return response.request.newBuilder()
                    .header("Authorization", "Bearer $userAccessToken")
                    .build()
            } else if (tokenResponse.code() == 401) {
                sessionManager.setExpired(true)
            }

        }
        return null
    }
}