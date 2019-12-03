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
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
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
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        btnLogin.setOnClickListener {
            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()
            if (txtEmail.isNullOrEmpty() || txtPassword.isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Zadajte meno aj heslo.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                login(txtEmail, txtPassword)
            }
        }
    }

    // TODO put into ViewModel
    private fun login(email: String, txtPassword: String) {
        var loginUser: LoggedUser

        Coroutines.create().launch {
            try {
                loginUser = loginModel.login(this@LoginActivity, email, txtPassword)
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