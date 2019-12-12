package com.example.mobv.model.repository

import android.content.Context
import com.example.mobv.Model.Room
import com.example.mobv.api.NotificationsApi
import com.example.mobv.model.Room
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.*
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage
import com.example.mobv.model.repositorymessage.IMessagingRepository
import retrofit2.Call

open class MessagingRepository:
    IMessagingRepository, CallHandler() {

    override fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<Contact>> = ZadanieApi.create(ctx).listContacts(ListContactRequest(uid))

        fetch(call, onSuccess, onFailure)
    }

    override fun readContact(ctx: Context, uid: String, contact: String, onSuccess: (List<ContactMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<ContactMessage>> = ZadanieApi.create(ctx).readContact(ReadContactRequest(uid, contact))

        fetch(call, onSuccess, onFailure)
    }

    override fun messageContact(ctx: Context, uid: String, contact: String, message: String, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<Void> = ZadanieApi.create(ctx).messageContact(MessageContactRequest(uid, contact, message))

        send(call, onSuccess, onFailure)
    }

    override fun notifyContact(ctx: Context, to: String, data: Map<String,String>, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<Void> = NotificationsApi.create(ctx).notifyContact(NotifyContactRequest(to, data))

        send(call, onSuccess, onFailure)
    }

    override fun getRooms(ctx: Context, uid: String, onSuccess: (List<Room>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<Room>> = ZadanieApi.create(ctx).listRooms(RoomListRequest(uid))

        fetch(call, onSuccess, onFailure)
    }

    override fun readRoom(ctx: Context, uid: String, room: String, onSuccess: (List<RoomMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<RoomMessage>> = ZadanieApi.create(ctx).readRoom(ReadRoomRequest(uid, room))

        fetch(call, onSuccess, onFailure)
    }

    override fun messageRoom(ctx: Context, uid: String, room: String, message: String, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<Void> = ZadanieApi.create(ctx).messageRoom(MessageRoomRequest(uid, room, message))

        send(call, onSuccess, onFailure)
    }

    override fun transformToGifMessage(gifId: String): String {
        return "gif:$gifId"
    }

    override fun isMessageGIF(message: String): Boolean {
        return message.startsWith("gif").and(message.length > 3 + 1 + 5)
    }

}