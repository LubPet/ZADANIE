package com.example.mobv.Model.mock

import android.content.Context
import com.example.mobv.Model.IMessagingRepository
import com.example.mobv.api.responses.Contact

class FakeMessagingRepository: IMessagingRepository {

    override fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit) {
        val contacts = ArrayList<Contact>()
        contacts.add(Contact("Prvy", "1"))
        contacts.add(Contact("Druhy", "2"))
        contacts.add(Contact("Treti", "3"))

        onSuccess(contacts)
    }


}