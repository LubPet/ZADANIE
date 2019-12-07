package com.example.mobv.Model.mock

import android.content.Context
import com.example.mobv.Model.IMessagingRepository
import com.example.mobv.Model.Room
import com.example.mobv.api.responses.Contact

class FakeMessagingRepository: IMessagingRepository {

    override fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit) {
        val contacts = ArrayList<Contact>()
        contacts.add(Contact("Prvy", "1"))
        contacts.add(Contact("Druhy", "2"))
        contacts.add(Contact("Treti", "3"))

        onSuccess(contacts)
    }

    override fun getRooms(ctx: Context, uid: String, onSuccess: (List<Room>) -> Unit, onFailure: (Throwable) -> Unit) {
        val rooms = ArrayList<Room>()
        rooms.add(Room("Prva", "2019-12-01 12:00:00"))
        rooms.add(Room("Druha", "2019-12-01 12:05:00"))
        rooms.add(Room("Tretia", "2019-12-01 12:10:00"))

        onSuccess(rooms)
    }


}