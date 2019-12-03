package com.example.mobv.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class Coroutines {

    companion object {

        fun create(): CoroutineScope {
            val viewModelJob = Job()

            return CoroutineScope(
                viewModelJob + Dispatchers.Main
            )
        }

    }
}