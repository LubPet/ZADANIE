package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.common.api.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

class RegisterActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.usernameReg)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.passwordReg)
        val btn_register = findViewById<Button>(R.id.btn_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Registrácia")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btn_register.setOnClickListener(View.OnClickListener {
            val txt_username = username.getText().toString()
            val txt_email = email.getText().toString()
            val txt_password = password.getText().toString()
            if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(
                    txt_password
                )
            ) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Nie všetky položky boli správne vyplnené.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                register(txt_username, txt_email, txt_password)
            }
        })
    }

    private fun register(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser!!
                    val userId = firebaseUser.uid
                    val reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    val hashMap: MutableMap<String, String> = HashMap()
                    hashMap["id"] = userId
                    hashMap["username"] = username
                    hashMap["imageURL"] = "default"
                    reference.setValue(hashMap).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "Registrácia bola úspešná.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@RegisterActivity, "Registrácia bola neúspešná.", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(this@RegisterActivity, "Registrácia bola neúspešná.", Toast.LENGTH_LONG).show()
                }
            }
    }

}