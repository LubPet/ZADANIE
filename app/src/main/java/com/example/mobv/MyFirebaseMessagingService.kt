package com.example.mobv

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.repository.UserRepository
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.SetFidRequest
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.launch


internal class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)

        var user: LoggedUser = SessionManager.get(this).getSessionData()!!
        var userRepository = UserRepository()
        Coroutines.create().launch {
            userRepository.setFID(this@MyFirebaseMessagingService, user.uid, s)

        }


    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG,"Data: " + remoteMessage.data.toString())

        if(remoteMessage.notification!= null){
            Log.d(TAG,"Notification: " + remoteMessage.notification!!.toString())
        }
    }
}