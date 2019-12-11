package com.example.mobv.model.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.mobv.model.LoggedUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId

class FirebaseIdRepository(val ctx: Context) {

    private val auth = FirebaseAuth.getInstance()

    fun getId(user: LoggedUser, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {

        //toto dostane token z FireBase
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener(ctx as Activity) { instanceIdResult ->
                val newToken = instanceIdResult.token
                Log.e("newToken", newToken)
                onSuccess(newToken)
            }
    }

}
