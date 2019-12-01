package com.example.mobv.api

import android.content.Context
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route

class TokenAuthenticator(val context: Context) : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        if ((response.request.header("OpinAuth")?.compareTo("accept")==0) && response.code==401){
            val userAccessToken = "c95332ee022df8c953ce470261efc695ecf3e784"

                if (!response.request.header("Authorization").equals("Bearer $userAccessToken")){
                    return null
                }

            val refreshToken= ""
            val user_id= ""

            val tokenResponse = ZadanieApi.create(context).tokenRefreshCall(RefreshTokenRequest(user_id, refreshToken)).execute()

            if (tokenResponse.isSuccessful){
                userAccessToken = tokenResponse.body()!!.accessToken
                uloz(tokenResponse.body()!!.accessToken) // store new access token
                uloz(tokenResponse.body()!!.refreshToken) // store new refresh token

                return response.request().newBuilder().header("Authorization","Bearer $userAccessToken").build()
            }
        }
        return null
    }
}