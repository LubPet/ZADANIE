package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RoomMessageViewModelFactory(
    val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomMessageViewModel::class.java)) {
            return RoomMessageViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}