package com.example.mobv.viewModels

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobv.MainActivity
import com.example.mobv.model.LoggedUser
import com.example.mobv.model.repository.FirebaseIdRepository
import com.example.mobv.model.repository.UserRepository
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.launch


class MainViewModel(val context: Context) : ViewModel() {

    private val userRepository = UserRepository()

    fun logout() {
        userRepository.logout(context)
    }

}