package com.example.mobv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments

        binding.loginViewModel = loginViewModel

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Prihlásenie")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
    }
}