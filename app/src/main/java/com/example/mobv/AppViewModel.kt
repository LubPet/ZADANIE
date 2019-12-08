package com.example.mobv

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.example.mobv.Model.FirebaseDAO
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.LoginModel
import com.example.mobv.session.SessionManager


class AppViewModel : ViewModel() {
/*    private val _name = MutableLiveData("Ada")

    val name: LiveData<String> = _name

    fun onLike() {
        _name.value = "Igor"
    }*/
    private val firebaseDAO = FirebaseDAO()
    private val loginModel = LoginModel()

    // variable to hold context
    private var context: Context? = null


    @BindingAdapter("android:onClick")
    fun setText(view: TextView, text: CharSequence?) { // Some checks removed for clarity
        this.context = view.context
    }

    fun onButtonClick() {
        _name = "ivan"
    }




    // TODO put into ViewModel
    private fun login(email: String, txtPassword: String) {
        var loginUser: LoggedUser

        Coroutines.create().launch {
            try {
                loginUser = loginModel.login(context, email, txtPassword)
                firebaseDAO.loginUser(email, txtPassword, { firebaseUser ->
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

        startActivity(intent)
        finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(context, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
    }

}