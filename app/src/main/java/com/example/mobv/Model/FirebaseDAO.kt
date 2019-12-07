package com.example.mobv.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class FirebaseDAO {

    private val auth = FirebaseAuth.getInstance()

    fun createUser(username: String, email: String, password: String, onSuccess: (FirebaseUser?) -> Unit, onFailure: (String?) -> Unit) {
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
                        task.exception?.printStackTrace()
                        onFailure(task.exception?.message)

                    } else {
                        onSuccess(firebaseUser)
                    }
                }

            } else {
                task.exception?.printStackTrace()
                onFailure(task.exception?.message)
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: (FirebaseUser?) -> Unit, onFailure: (String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(auth.currentUser!!)
                } else {
                    task.exception?.printStackTrace()
                    onFailure(task.exception?.message)
                }
            }
    }

    fun logoutUser() {
        auth.signOut()
    }
}
