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
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.launch


class LoginViewModel(val context: Context) : ViewModel() {
/*  private val _name = MutableLiveData("Ada")

    val name: LiveData<String> = _name

    fun onLike() {
        _name.value = "Igor"
    }*/

    private val firebaseIdRepository = FirebaseIdRepository(context)
    private val userRepository = UserRepository()

    fun login(username: String, txtPassword: String, onSuccess: (LoggedUser) -> Unit, onFailure: () -> Unit) {
        var loginUser: LoggedUser

        Coroutines.create().launch {
            try {
                loginUser = userRepository.login(context, username, txtPassword)
                firebaseIdRepository.getId(loginUser, { id ->
                    loginUser.fid = id
                    loginUser.username = username

                    Coroutines.create().launch {
                        userRepository.setFID(context, loginUser.uid, id)
                    }

                    onSuccess(loginUser)
                }, {
                    onFailure()
                })
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        }
    }


}