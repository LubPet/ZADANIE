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
import android.webkit.WebView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobv.Model.GifResource
import com.example.mobv.Model.giphy.repository.GiphyRepository
import com.example.mobv.R
import com.example.mobv.api.responses.Contact
import com.example.mobv.databinding.ActivityMessageBinding
import kotlin.collections.ArrayList


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
            messages.value!!.add(Chat(loggedUser.uid, contact.id, message + " KAR"))
        }, {
            it.printStackTrace()
            Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })



        readMessages(contact.id)
        messageContent.setText("")
    }

    fun readMessages(receiver: String) {
        loggedUser = SessionManager.get(context).getSessionData()!!
        messagingRepository.readContact(context, loggedUser.uid, getContact().id, { contactMessages ->
            val list = LinkedList<Chat>()
            contactMessages.forEach { message ->
                val chat = Chat()
                if (message.uid == loggedUser.uid) {
                    chat.sender = loggedUser.uid
                    chat.senderName = loggedUser.username
                    chat.receiver = message.contact_name

                    val map = mapOf("body" to message.message
                                   ,"title" to "Správa z MOBV!")

                    messagingRepository.notifyContact(context, message.contact_fid, map , {
                        Log.i("Notifikacia","Odosielanie notifikácie prešlo")
                    }, {
                        Log.e("Notifikacia","Odosielanie notifikácie neprešlo")
                    })
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