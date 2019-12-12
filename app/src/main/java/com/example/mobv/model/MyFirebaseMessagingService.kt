package com.example.mobv.model

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.SetFidRequest
import com.example.mobv.model.LoggedUser
import com.example.mobv.model.repository.UserRepository
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.launch


internal class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)

        val userRepository = UserRepository()
        val user: LoggedUser? = SessionManager.get(this@MyFirebaseMessagingService).getSessionData()

        if (user == null) {
            Log.e("NEW TOKEN", "No user session")
            return
        }

        Coroutines.create().launch {
            userRepository.setFID(this@MyFirebaseMessagingService, user.uid, s)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Data: " + remoteMessage.data.toString())
        Log.d(TAG, "Notification: " + remoteMessage.notification!!.toString())
    }
}