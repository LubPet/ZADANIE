package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobv.Model.FirebaseDAO
import com.example.mobv.Model.LoginModel
import com.example.mobv.Model.LoggedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class LoginActivity : AppCompatActivity() {

    private val firebaseDAO = FirebaseDAO()
    private val loginModel = LoginModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Prihlásenie")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        btnLogin.setOnClickListener {
            val txtUsername = username.text.toString()
            val txtPassword = password.text.toString()
            if (txtUsername.isNullOrEmpty() || txtPassword.isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Zadajte meno aj heslo.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                login(txtUsername, txtPassword)
            }
        }
    }

    // TODO put into ViewModel
    private fun login(txtUsername: String, txtPassword: String) {
        val viewModelJob = Job()
        val coroutineScope = CoroutineScope(
            viewModelJob + Dispatchers.Main
        )

        var loginUser: LoggedUser
        coroutineScope.launch {
            try {
                loginUser = loginModel.login(this@LoginActivity, txtUsername, txtPassword)
                firebaseDAO.loginUser(txtUsername, txtPassword) { firebaseUser ->
                    if (firebaseUser != null) {
                        onLoginSuccess(loginUser)
                    } else {
                        onLoginFailure()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onLoginFailure()
            }
        }
    }

    private fun onLoginSuccess(body: LoggedUser) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
        finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(this@LoginActivity, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
    }
}