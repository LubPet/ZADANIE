package com.example.mobv.session

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.mobv.model.LoggedUser
import com.google.gson.Gson


class SessionManager {

    private var ctx: Context? = null

    constructor(ctx: Context) {
        this.ctx = ctx
    }

    fun saveSessionData(user: LoggedUser) {
        val sharedPreferences = loadPrefs()
        val editor = sharedPreferences.edit()

        editor.putString("user", Gson().toJson(user))
        editor.apply()
    }

    fun getSessionData(): LoggedUser? {
        val sharedPreferences = loadPrefs()
        val json = sharedPreferences.getString("user", null) ?: return null

        return Gson().fromJson(json, LoggedUser::class.java)
    }

    fun clearSessionData() {
        val sharedPreferences = loadPrefs()
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun setExpired(expired: Boolean) {
        val loggedUser = getSessionData()
        loggedUser!!.expired = expired
        saveSessionData(loggedUser)
    }

    private fun loadPrefs(): SharedPreferences {
        return ctx!!.getSharedPreferences("Session", MODE_PRIVATE)
    }

    companion object {

        fun get(ctx: Context): SessionManager {
            return SessionManager(ctx)
        }

    }
}