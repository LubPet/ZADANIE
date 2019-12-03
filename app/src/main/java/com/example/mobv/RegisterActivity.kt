package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobv.Model.FirebaseDAO
import com.example.mobv.Model.RegisterModel
import com.example.mobv.Model.LoggedUser
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private val firebaseDAO = FirebaseDAO()
    private val registerModel = RegisterModel() // TODO put into ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.usernameReg)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.passwordReg)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Registrácia"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnRegister.setOnClickListener(View.OnClickListener {
            val txtUsername = username.text.toString()
            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()
            if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Nie všetky položky boli správne vyplnené.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                register(txtUsername, txtEmail, txtPassword)
            }
        })
    }

    // TODO put into ViewModel
    private fun register(username: String, email: String, password: String) {
        var user : LoggedUser
        Coroutines.create().launch {
            try {
                user = registerModel.register(this@RegisterActivity, email, password)
                firebaseDAO.createUser(username, email, password, { firebaseUser ->
                    user.fid = firebaseUser!!.uid

                    SessionManager.get(this@RegisterActivity).saveSessionData(user)

                    Coroutines.create().launch {
                        registerModel.setFID(this@RegisterActivity, user.uid, user.fid)
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
        Toast.makeText(this@RegisterActivity, "Registrácia bola úspešná.", Toast.LENGTH_LONG).show()
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun onRegisterFailure() {
        Toast.makeText(
            this@RegisterActivity,
            "Registrácia bola neúspešná.",
            Toast.LENGTH_LONG
        ).show()
    }
}


