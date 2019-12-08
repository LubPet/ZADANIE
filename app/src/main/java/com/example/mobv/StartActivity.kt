package com.example.mobv;

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mobv.Model.repository.AvailableRoomsRepository
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

        test()
    }

    fun test() {
        val repo = AvailableRoomsRepository.create(this)
        repo.getAvailableRooms({
            println(it)
        }, {
            println("Fail")
        })

    }


}
