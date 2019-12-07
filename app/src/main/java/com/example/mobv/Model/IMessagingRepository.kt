package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.responses.Contact

interface IMessagingRepository {

    suspend fun getContacts(ctx: Context, uid: String): List<Contact>

}
