package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class RoomsViewModelFactory(
    val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomsViewModel::class.java)) {
            return RoomsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}