package com.example.mobv.viewModels

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobv.MainActivity
import com.example.mobv.model.LoggedUser
import com.example.mobv.model.repository.FirebaseIdRepository
import com.example.mobv.model.repository.UserRepository
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

    fun register(username: String, password: String, onRegisterSuccess: (LoggedUser) -> Unit, onRegisterFailure: () -> Unit) {
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


}