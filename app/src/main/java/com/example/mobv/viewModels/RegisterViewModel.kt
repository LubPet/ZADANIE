package com.example.mobv.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.mobv.MainActivity
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.repository.FirebaseIdRepository
import com.example.mobv.Model.repository.UserRepository
import com.example.mobv.MyFirebaseMessagingService
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.launch


class RegisterViewModel(val context: Context) : ViewModel() {
/*  private val _name = MutableLiveData("Ada")

    val name: LiveData<String> = _name

    fun onLike() {
        _name.value = "Igor"
    }*/

    private val firebaseIdRepository = FirebaseIdRepository(context)
    private val userRepository = UserRepository()

    fun register(username: String, password: String) {
        var user : LoggedUser
        Coroutines.create().launch {
            try {
                user = userRepository.register(context, username, password)
                firebaseIdRepository.getId(user, { id ->
                    user.fid = id
                    user.username = username
                    SessionManager.get(context).saveSessionData(user)

                    Coroutines.create().launch {
                        userRepository.setFID(context, user.uid, user.fid)
                        onRegisterSuccess(user)
                    }

                }, {
                    onRegisterFailure()
                })

            } catch (e: Exception) {
                e.printStackTrace()
                onRegisterFailure()
            }
        }
    }

    private fun onRegisterSuccess(loggedUser: LoggedUser) {
        Toast.makeText(context, "Registrácia bola úspešná.", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        (context).startActivity(intent)
    }

    private fun onRegisterFailure() {
        Toast.makeText(
            context,
            "Registrácia bola neúspešná.",
            Toast.LENGTH_LONG
        ).show()
    }
}