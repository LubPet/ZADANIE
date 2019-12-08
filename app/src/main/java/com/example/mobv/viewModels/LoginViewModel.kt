package com.example.mobv.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobv.model.LoggedUser
import com.example.mobv.model.LoginModel


class LoginViewModel(val context: Context) : ViewModel() {
/*  private val _name = MutableLiveData("Ada")

    val name: LiveData<String> = _name

    fun onLike() {
        _name.value = "Igor"
    }*/

    private val loginModel = LoginModel()

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.w("tag", "onTextChanged $s")
    }

    fun login() {
        var loginUser: LoggedUser
/*
        Coroutines.create().launch {
            try {
                loginUser = loginModel.login(context, "doplnit mail", "doplnit password")
                firebaseDAO.loginUser("doplnit mail", "doplnit password", { firebaseUser ->
                    loginUser.fid = firebaseUser!!.uid
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
        Toast.makeText(context, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()*/
    }

}