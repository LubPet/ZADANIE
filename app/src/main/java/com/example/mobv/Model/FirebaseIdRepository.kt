package com.example.mobv.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class FirebaseIdRepository {

    private val auth = FirebaseAuth.getInstance()

    fun getId(user: LoggedUser, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        // TODO implement
        onSuccess("TEST")
    }

}
