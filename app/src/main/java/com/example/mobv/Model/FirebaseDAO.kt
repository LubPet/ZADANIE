package com.example.mobv.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class FirebaseDAO {

    private val auth = FirebaseAuth.getInstance()

    fun createUser(username: String, email: String, password: String, callback: (FirebaseUser?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth.currentUser!!
                val userId = firebaseUser.uid
                val reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                val hashMap: MutableMap<String, String> = HashMap()
                hashMap["id"] = userId
                hashMap["username"] = username
                hashMap["imageURL"] = "default"
                reference.setValue(hashMap).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        callback(null)

                    } else {
                        callback(firebaseUser)
                    }
                }

            } else {
                callback(null)
            }
        }
    }

    fun loginUser(username: String, password: String, callback: (FirebaseUser?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(auth.currentUser!!)
                } else {
                    callback(null)
                }
            }
    }
}
