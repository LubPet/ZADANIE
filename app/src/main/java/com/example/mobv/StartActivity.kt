package com.example.mobv;

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.mobv.Model.giphy.repository.GiphyRepository
import com.example.mobv.session.SessionManager

class StartActivity: AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val loggedUser = SessionManager.get(this@StartActivity).getSessionData()

        if (loggedUser != null) {

            if (!loggedUser.expired) {
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
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
