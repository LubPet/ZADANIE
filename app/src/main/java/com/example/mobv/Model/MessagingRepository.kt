package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.*
import com.example.mobv.api.responses.Contact
import com.example.mobv.api.responses.ContactMessage
import com.example.mobv.api.responses.RoomMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

open class MessagingRepository: IMessagingRepository {

    override fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<Contact>> = ZadanieApi.create(ctx).listContacts(ListContactRequest(uid))

        fetch(call, onSuccess, onFailure)
    }

    override fun readContact(ctx: Context, uid: String, contact: String, onSuccess: (List<ContactMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<ContactMessage>> = ZadanieApi.create(ctx).readContact(ReadContactRequest(uid, contact))

        fetch(call, onSuccess, onFailure)
    }

    override fun messageContact(ctx: Context, uid: String, contact: String, message: String, onSuccess: (Any) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<Any> = ZadanieApi.create(ctx).messageContact(MessageContactRequest(uid, contact, message))

        fetch(call, onSuccess, onFailure)
    }

    override fun getRooms(ctx: Context, uid: String, onSuccess: (List<Room>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<Room>> = ZadanieApi.create(ctx).listRooms(RoomListRequest(uid))

        fetch(call, onSuccess, onFailure)
    }

    override fun readRoom(ctx: Context, uid: String, room: String, onSuccess: (List<RoomMessage>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<RoomMessage>> = ZadanieApi.create(ctx).readRoom(ReadRoomRequest(uid, room))

        fetch(call, onSuccess, onFailure)
    }

    override fun messageRoom(ctx: Context, uid: String, room: String, message: String, onSuccess: (Any) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<Any> = ZadanieApi.create(ctx).messageRoom(MessageRoomRequest(uid, room, message))

        fetch(call, onSuccess, onFailure)
    }

    private fun <T> fetch(call: Call<T>, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        return call.enqueue(object: Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == 200) {
                    onSuccess(response.body()!!)

                } else {
                    onFailure(Exception("Failed to get data " + response.code() + " " + response.message() + " " + response.body()))
                }
            }
        })
    }

}