package com.example.mobv

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

internal class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.data!= null){
            Log.d(TAG,"Data: " + remoteMessage.data.toString())
        }
        if(remoteMessage.notification!= null){
            Log.d(TAG,"Notification: " + remoteMessage.notification!!.toString())
        }
    }
}