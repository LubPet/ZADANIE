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
import com.google.firebase.auth.FirebaseAuth
import java.net.HttpURLConnection
import java.net.URL

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class LoginActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Prihlásenie")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        btn_login.setOnClickListener{
            val txt_username = username.text.toString()
            val txt_password = password.text.toString()
            if (txt_username.isNullOrEmpty() || txt_password.isNullOrEmpty()) {
                Toast.makeText(this@LoginActivity, "Zadajte meno aj heslo.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(txt_username, txt_password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Nesprávne meno alebo heslo.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}