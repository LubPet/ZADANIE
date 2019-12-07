package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.responses.Contact
import retrofit2.Call

interface IMessagingRepository {

    fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit)

}
