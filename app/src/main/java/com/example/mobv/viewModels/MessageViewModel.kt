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
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.api.responses.Contact
import com.example.mobv.model.giphy.repository.GiphyRepository


class MessageViewModel(val context: Context) : ViewModel() {

    private var loggedUser: LoggedUser = LoggedUser()
    private var contactFid: String = ""

    var messages : MutableLiveData<LinkedList<Chat>> = MutableLiveData()

    private val messagingRepository = MessagingRepositoryFactory.create()
    private val giphyRepository = GiphyRepository.create(context)

    lateinit var messageContent: EditText
    lateinit var recyclerView: RecyclerView

    fun getMessages(): LiveData<LinkedList<Chat>> {
        return messages
    }

    fun sendGifMessage(id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        sendMessage(messagingRepository.transformToGifMessage(id), onSuccess, onFailure)
    }

    fun sendMessage(message : String = "", onSuccess: () -> Unit, onFailure: () -> Unit) {
        loggedUser = SessionManager.get(context).getSessionData()!!
        val contact = getContact()

        val messagingRepository = MessagingRepositoryFactory.create()
        messagingRepository.messageContact(context, loggedUser.uid, contact.id, message, {
            messages.value!!.add(Chat(loggedUser.uid, contact.id, message))

            readMessages()
            notifyUser(message)
            messageContent.setText("")
            onSuccess()
        }, {
            it.printStackTrace()
            onFailure()
        })


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

                    contactFid = message.contact_fid
                } else {
                    chat.receiver = loggedUser.uid
                    chat.sender = message.contact_name
                    chat.senderName =  message.contact_name
                }
                chat.time = message.getTime()
                chat.message = message.message
                chat.isGif = messagingRepository.isMessageGIF(message.message)
                if (chat.isGif)
                    chat.gifUrl = giphyRepository.getURL(message.message)
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

    private fun notifyUser(message: String) {
        val map = mapOf("body" to message, "title" to "Správa z MOBV!")
        messagingRepository.notifyContact(context, contactFid, map, "type_a", map, {
            Log.i("Notifikacia","Odosielanie notifikácie prešlo")
        }, {
            Log.e("Notifikacia","Odosielanie notifikácie neprešlo")
        })
    }

    fun getContact(): Contact {
        val intent = (context as Activity).intent
        val user = intent.getSerializableExtra("user")
        return (user as Contact)
    }

}