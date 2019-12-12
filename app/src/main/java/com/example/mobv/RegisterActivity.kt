package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.databinding.ActivityRegisterBinding
import com.example.mobv.model.LoggedUser
import com.example.mobv.viewModels.RegisterViewModel
import com.example.mobv.viewModels.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val viewModelFactory = RegisterViewModelFactory(this)
    private val registerViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_register
        )
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.registerViewModel = registerViewModel
        binding.registerListener = RegisterListener { username, password ->
            registerViewModel.register(username, password, {
                onRegisterSuccess(it)
            }, {
                onRegisterFailure()
            })
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Registrácia"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun onRegisterSuccess(loggedUser: LoggedUser) {
        Toast.makeText(this, "Registrácia bola úspešná.", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        (this).startActivity(intent)
    }

    private fun onRegisterFailure() {
        Toast.makeText(
            this,
            "Registrácia bola neúspešná.",
            Toast.LENGTH_LONG
        ).show()
    }

    class RegisterListener(val callback: (String, String) -> Unit) {

        fun register(username: String, password: String) {
            callback(username, password)
        }

    }
}


