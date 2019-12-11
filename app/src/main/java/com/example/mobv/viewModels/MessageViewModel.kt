package com.example.mobv.viewModels

import android.content.Context
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
import com.example.mobv.api.responses.Contact


class MessageViewModel(val context: Context) : ViewModel() {

    private var loggedUser: LoggedUser = LoggedUser()

    var messages : MutableLiveData<LinkedList<Chat>> = MutableLiveData()

    private val messagingRepository = MessagingRepositoryFactory.create()

    lateinit var messageContent: EditText

    fun getMessages(): LiveData<LinkedList<Chat>> {
        return messages
    }

    fun sendMessage(message : String = "") {
        loggedUser = SessionManager.get(context).getSessionData()!!
        val contact = getContact()

        val messagingRepository = MessagingRepositoryFactory.create()
        messagingRepository.messageContact(context, loggedUser.uid, contact.id, message, {
            messages.value!!.add(Chat(loggedUser.uid, contact.id, message))
        }, {
            it.printStackTrace()
            Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })

        readMessages()
        messageContent.setText("")
    }

    fun readMessages() {
        loggedUser = SessionManager.get(context).getSessionData()!!
        messagingRepository.readContact(context, loggedUser.uid, getContact().id, { contactMessages ->
            val list = LinkedList<Chat>()
            contactMessages.forEach { message ->
                val chat = Chat()
                if (message.uid == loggedUser.uid) {
                    chat.sender = loggedUser.uid
                    chat.senderName = loggedUser.username
                    chat.receiver = message.contact_name
                } else {
                    chat.receiver = loggedUser.uid
                    chat.sender = message.contact_name
                    chat.senderName =  message.contact_name
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

    fun getContact(): Contact {
        val intent = (context as Activity).intent
        val user = intent.getSerializableExtra("user")
        return (user as Contact)
    }

}