package com.example.mobv.Model.repository

import android.content.Context
import com.example.mobv.Model.Room
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

    fun messageRoom(
        ctx: Context,
        uid: String,
        room: String,
        message: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )

    fun messageContact(
        ctx: Context,
        uid: String,
        contact: String,
        message: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )
    fun notifyContact(
        ctx: Context,
        to: String,
        data: Map<String,String>,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    )
}
