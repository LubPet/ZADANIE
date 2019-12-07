package com.example.mobv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobv.Model.LoggedUser

import com.example.mobv.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity: AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val loggedUser = SessionManager.get(this@StartActivity).getSessionData()

        if (loggedUser != null) {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_start)

        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)

        login.setOnClickListener { view ->
            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
        }

        register.setOnClickListener {view ->
            startActivity(Intent(this@StartActivity, RegisterActivity::class.java))
        }
    }
}
