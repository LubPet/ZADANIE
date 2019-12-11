package com.example.mobv.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Adapter.MessageAdapter
import com.example.mobv.MainActivity
import com.example.mobv.MessageActivity
import com.example.mobv.Model.Chat
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.session.SessionManager
import com.example.mobv.utils.Coroutines
import kotlinx.coroutines.launch
import java.util.*
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobv.Model.ChatRoom
import com.example.mobv.R
import com.example.mobv.databinding.ActivityMessageBinding
import kotlin.collections.ArrayList


class RoomMessageViewModel(val context: Context) : ViewModel() {

    private var loggedUser: LoggedUser = LoggedUser()

    var messages: MutableLiveData<LinkedList<Chat>> = MutableLiveData()

    private val messagingRepository = MessagingRepositoryFactory.create()

    lateinit var messageContent: EditText

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

            messages.postValue(list)
        },
            {
                it.printStackTrace()
                Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
            })
    }

    private fun getRoom(): ChatRoom {
        val intent = (context as Activity).intent
        val room = intent.getSerializableExtra("room")
        return (room as ChatRoom)
    }

}