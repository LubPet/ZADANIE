package com.example.mobv.Model

import android.content.Context
import com.example.mobv.Model.serialization.StringToListDeserializer
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.ListContactRequest
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage

class MessagingRepository: IMessagingRepository {

    override suspend fun getContacts(ctx: Context, uid: String): List<Contact> {
        val response = ZadanieApi.create(ctx).listContacts(ListContactRequest(uid))

        return StringToListDeserializer<Contact>().deserialize(response.string())
    }

}