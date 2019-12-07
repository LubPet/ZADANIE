package com.example.mobv.Model.mock

import android.content.Context
import com.example.mobv.Model.IMessagingRepository
import com.example.mobv.api.responses.Contact

class FakeMessagingRepository: IMessagingRepository {

    override suspend fun getContacts(ctx: Context, uid: String): List<Contact> {
        val contacts = ArrayList<Contact>()
        contacts.add(Contact("Prvy", "1"))
        contacts.add(Contact("Druhy", "2"))
        contacts.add(Contact("Treti", "3"))

        return contacts
    }


}