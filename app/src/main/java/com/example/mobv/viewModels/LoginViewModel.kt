package com.example.mobv.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobv.MainActivity
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.repository.FirebaseIdRepository
import com.example.mobv.Model.repository.UserRepository
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.launch


class LoginViewModel(val context: Context) : ViewModel() {
/*  private val _name = MutableLiveData("Ada")

    val name: LiveData<String> = _name

    fun onLike() {
        _name.value = "Igor"
    }*/

    private val firebaseIdRepository = FirebaseIdRepository()
    private val userRepository = UserRepository()

    fun login( username: String, txtPassword: String) {
        var loginUser: LoggedUser

        Coroutines.create().launch {
            try {
                loginUser = userRepository.login(context, username, txtPassword)
                firebaseIdRepository.getId(loginUser, { id ->
                    loginUser.fid = id
                    loginUser.username = username
                    onLoginSuccess(loginUser)
                }, {
                    onLoginFailure()
                })
            } catch (e: Exception) {
                e.printStackTrace()
                onLoginFailure()
            }
        }
    }

    private fun onLoginSuccess(loggedUser: LoggedUser) {
        SessionManager.get(context).saveSessionData(loggedUser)


        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        (context).startActivity(intent)
    }

    private fun onLoginFailure() {
        Toast.makeText(context, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
    }
}