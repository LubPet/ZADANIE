package com.example.mobv.api

import android.content.Context
import com.example.mobv.api.requests.RefreshTokenRequest
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.Authenticator

class TokenAuthenticator(val context: Context) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val userAccessToken = "" // TODO
        val userId = "" // TODO

        if (response.request.header("OpinAuth")?.compareTo("accept") == 0 && response.code == 401) {

            if (!response.request.header("Authorization").equals("Bearer $userAccessToken")) {
                return null
            }

            val refreshToken = "" // TODO

            val tokenResponse = ZadanieApi.create(context)
                .tokenRefreshCall(RefreshTokenRequest(userId, refreshToken)).execute()

            if (tokenResponse.isSuccessful) {
//                userAccessToken = tokenResponse.body!!.accessToken
//                uloz(tokenResponse.body()!!.accessToken) // store new access token
//                uloz(tokenResponse.body()!!.refreshToken) // store new refresh token

                return response.request.newBuilder()
                    .header("Authorization", "Bearer $userAccessToken")
                    .build()
            }

        }
        return null
    }
}