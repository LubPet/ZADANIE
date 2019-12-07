package com.example.mobv.Model.mock

import android.content.Context
import com.example.mobv.Model.IMessagingRepository
import com.example.mobv.Model.MessagingRepository
import com.example.mobv.Model.Room
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage

class FakeMessagingRepository: MessagingRepository() {

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

    override fun readContact(ctx: Context, uid: String, contact: String, onSuccess: (List<ContactMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val messages = ArrayList<ContactMessage>()
        messages.add(ContactMessage("uid", "message", "2019-12-01 12:00:00", "Libor", "Maros", "0000", "0001"))
        messages.add(ContactMessage("uid", "message", "2019-12-01 12:05:00", "Libor", "Maros", "0000", "0001"))
        messages.add(ContactMessage("uid", "message", "2019-12-01 12:06:00", "Libor", "Maros", "0000", "0001"))

        onSuccess(messages)
    }

    override fun readRoom(ctx: Context, uid: String, room: String, onSuccess: (List<RoomMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val messages = ArrayList<RoomMessage>()
        messages.add(RoomMessage("1", "room1","message","2019-12-01 12:00:00", "Tomas"))
        messages.add(RoomMessage("2", "room1","message","2019-12-01 12:05:00", "Mato"))
        messages.add(RoomMessage("3", "room1","message","2019-12-01 12:06:00", "Libor"))

        onSuccess(messages)
    }


}