package com.example.mobv.viewModels

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Model.Chat
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.session.SessionManager
import java.util.*
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobv.Model.ChatRoom


class RoomMessageViewModel(val context: Context) : ViewModel() {

    private var loggedUser: LoggedUser = LoggedUser()

    var messages: MutableLiveData<LinkedList<Chat>> = MutableLiveData()

    private val messagingRepository = MessagingRepositoryFactory.create()

    lateinit var messageContent: EditText

    lateinit var recyclerView : RecyclerView

    fun getMessages(): LiveData<LinkedList<Chat>> {
        return messages
    }

    fun sendMessage(message: String = "") {
        val room = getRoom()
        loggedUser = SessionManager.get(context).getSessionData()!!
        val messagingRepository = MessagingRepositoryFactory.create()
        messagingRepository.messageRoom(context, loggedUser.uid, room.getName(), message, {
            messages.value!!.add(Chat(loggedUser.uid, room.getName(), message)) // TODO

        }, {
            it.printStackTrace()
            Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })

        readMessages()
        notifyRoom(message)
        messageContent.setText("")
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
                list.add(chat)
            }
            recyclerView.scrollToPosition( contactMessages.size - 1)
            messages.postValue(list)
        },
            {
                it.printStackTrace()
                Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
            })
    }

    private fun notifyRoom(message: String) {
        val map = mapOf("body" to message, "title" to "Správa z MOBV!")

        messagingRepository.notifyContact(context, getRoom().getName(), map , {
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

}