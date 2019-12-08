package com.example.mobv.Model.repository

import com.example.mobv.Model.LoggedUser
import com.google.firebase.auth.FirebaseAuth

class FirebaseIdRepository {

    private val auth = FirebaseAuth.getInstance()

    fun getId(user: LoggedUser, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        // TODO implement
        onSuccess("TEST")
    }

}
