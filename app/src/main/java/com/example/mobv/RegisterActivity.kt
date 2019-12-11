package com.example.mobv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mobv.databinding.ActivityRegisterBinding
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

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Registrácia"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}


