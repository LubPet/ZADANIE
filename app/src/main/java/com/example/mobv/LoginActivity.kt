package com.example.mobv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.Model.FirebaseDAO
import com.example.mobv.Model.LoginModel
import com.example.mobv.Model.LoggedUser
import com.example.mobv.databinding.ActivityLoginBinding
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
class LoginActivity : AppCompatActivity() {



    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments

        binding.viewModel = viewModel

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Prihlásenie")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
    }
}