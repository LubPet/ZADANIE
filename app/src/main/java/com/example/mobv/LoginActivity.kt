package com.example.mobv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.R
import com.example.mobv.databinding.ActivityLoginBinding
import com.example.mobv.viewModels.LoginViewModel
import com.example.mobv.viewModels.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModelFactory = LoginViewModelFactory(this)
    private val loginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.loginViewModel = loginViewModel
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Prihlásenie"
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
    }
}