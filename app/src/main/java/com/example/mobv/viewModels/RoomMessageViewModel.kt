package com.example.mobv.viewModels

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mobv.model.Chat
import com.example.mobv.model.LoggedUser
import com.example.mobv.model.factory.MessagingRepositoryFactory
import com.example.mobv.session.SessionManager
import java.util.*
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.model.ChatRoom
import com.example.mobv.model.WifiRoom
import com.example.mobv.model.giphy.repository.GiphyRepository
import com.example.mobv.model.repository.AvailableRoomsRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Exception
import java.lang.IllegalArgumentException


class RoomMessageViewModel(val context: Context) : ViewModel() {

    private var loggedUser: LoggedUser = LoggedUser()

    var messages: MutableLiveData<LinkedList<Chat>> = MutableLiveData()

    private val messagingRepository = MessagingRepositoryFactory.create()
    private val availableRoomsRepository = AvailableRoomsRepository.create(context)
    private val giphyRepository = GiphyRepository.create(context)

    lateinit var messageContent: EditText

    lateinit var recyclerView: RecyclerView

    fun getMessages(): LiveData<LinkedList<Chat>> {
        return messages
    }

    fun sendGifMessage(id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        sendMessage(messagingRepository.transformToGifMessage(id), onSuccess, onFailure)
    }

    fun sendMessage(message: String = "", onSuccess: () -> Unit, onFailure: () -> Unit) {
        val room = getRoom()

        if (!isConnected()) {
            throw IllegalArgumentException("not connected")
        }

        loggedUser = SessionManager.get(context).getSessionData()!!
        val messagingRepository = MessagingRepositoryFactory.create()
        messagingRepository.messageRoom(context, loggedUser.uid, room.getName(), message, {
            messages.value!!.add(Chat(loggedUser.uid, room.getName(), message))

            readMessages()
            notifyRoom(message, loggedUser.username)

            try {
                val grim = room.getName().replace(":","")
                FirebaseMessaging.getInstance().subscribeToTopic(grim)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Success: subscribed to - " + room.getName(), Toast.LENGTH_LONG).show()
                    }
            } catch (e: Exception) {
                Log.wtf("Subscribing fail", e)
            }

            messageContent.setText("")

            onSuccess()

        }, {
            it.printStackTrace()
            onFailure()
        })

    }

    fun readMessages() {
        val room = getRoom()
        loggedUser = SessionManager.get(context).getSessionData()!!
        messagingRepository.readRoom(context, loggedUser.uid, room.getName(), { contactMessages ->
                val list = LinkedList<Chat>()
                contactMessages.forEach { message ->
                    val chat = Chat()
                    if (message.uid == loggedUser.uid) {
                        chat.sender = loggedUser.uid
                        chat.senderName = loggedUser.username
                        chat.receiver = room.getName()
                    } else {
                        chat.receiver = room.getName()
                        chat.sender = message.name
                        chat.senderName = message.name
                        chat.uid = message.uid
                    }
                    chat.time = message.getTime()
                    chat.message = message.message
                    chat.isGif = messagingRepository.isMessageGIF(message.message)
                    if (chat.isGif)
                        chat.gifUrl = giphyRepository.getURL(message.message)
                    list.add(chat)
                }
                recyclerView.scrollToPosition(contactMessages.size - 1)
                messages.postValue(list)
            },
            {
                it.printStackTrace()
                Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
            })
    }

    private fun notifyRoom(message: String, name: String) {
        val map = mapOf("body" to message, "title" to name)

        val grim = name.replace("_","")

        messagingRepository.notifyContact(context, grim, map, "type_a", map, {
            Log.i("Notifikacia","Odosielanie notifikácie prešlo")
        }, {
            Log.e("Notifikacia","Odosielanie notifikácie neprešlo")
        })
    }

    private fun getRoom(): ChatRoom {
        val intent = (context as Activity).intent
        val room = intent.getSerializableExtra("room")
        return (room as ChatRoom)
    }


    private fun isConnected(): Boolean {
        val currentRoom = getRoom()
        val wifiRooms : List<WifiRoom> = availableRoomsRepository.getAvailableRooms()
        return (wifiRooms.any { it.getName() == currentRoom.getName() })
    }

}