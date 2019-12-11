package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GifsViewModelFactory(
    val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GifsViewModel::class.java)) {
            return GifsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}