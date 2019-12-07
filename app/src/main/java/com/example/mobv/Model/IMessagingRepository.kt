package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage

interface IMessagingRepository {

    fun getContacts(
        ctx: Context,
        uid: String,
        onSuccess: (List<Contact>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun getRooms(
        ctx: Context,
        uid: String,
        onSuccess: (List<Room>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun readContact(
        ctx: Context,
        uid: String,
        contact: String,
        onSuccess: (List<ContactMessage>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun readRoom(
        ctx: Context,
        uid: String,
        room: String,
        onSuccess: (List<RoomMessage>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}
