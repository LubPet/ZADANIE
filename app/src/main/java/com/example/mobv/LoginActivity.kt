package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobv.Model.FirebaseIdRepository
import com.example.mobv.Model.UserRepository
import com.example.mobv.Model.LoggedUser
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.launch

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class LoginActivity : AppCompatActivity() {

    private val firebaseIdRepository = FirebaseIdRepository()
    private val userRepository = UserRepository()

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
            if (txtUsername.isEmpty() || txtPassword.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Zadajte meno aj heslo.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                login(txtUsername, txtPassword)
            }
        }
    }

    // TODO put into ViewModel
    private fun login(username: String, txtPassword: String) {
        var loginUser: LoggedUser

        Coroutines.create().launch {
            try {
                loginUser = userRepository.login(this@LoginActivity, username, txtPassword)
                firebaseIdRepository.getId(loginUser, { id ->
                    loginUser.fid = id
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
        SessionManager.get(this@LoginActivity).saveSessionData(loggedUser)

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
        finish()
    }

    private fun onLoginFailure() {
        Toast.makeText(this@LoginActivity, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
    }
}