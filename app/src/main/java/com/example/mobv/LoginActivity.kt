package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.R
import com.example.mobv.databinding.ActivityLoginBinding
import com.example.mobv.model.LoggedUser
import com.example.mobv.session.SessionManager
import com.example.mobv.viewModels.LoginViewModel
import com.example.mobv.viewModels.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModelFactory = LoginViewModelFactory(this)
    private val loginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.loginViewModel = loginViewModel
        binding.loginListener = LoginListener { username, password ->
            loginViewModel.login(username, password, {
                onLoginSuccess(it)
            }, {
                onLoginFailure()
            })
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Prihlásenie"
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
    }

    private fun onLoginSuccess(loggedUser: LoggedUser) {
        SessionManager.get(this).saveSessionData(loggedUser)


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        (this).startActivity(intent)
    }

    private fun onLoginFailure() {
        Toast.makeText(this, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
    }

    class LoginListener(val callback: (String, String) -> Unit) {

        fun login(username: String, password: String) {
            callback(username, password)
        }

    }
}