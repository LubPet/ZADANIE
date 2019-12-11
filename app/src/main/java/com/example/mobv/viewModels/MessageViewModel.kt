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
        val messagingRepository = MessagingRepositoryFactory.create()
        messagingRepository.messageContact(context, loggedUser.uid, getContactId(), message, {
            messages.value!!.add(Chat(loggedUser.uid, getContactId(), message))
        }, {
            it.printStackTrace()
            Toast.makeText(context, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })

        readMessages(getContactId())
        messageContent.setText("")
    }

    fun readMessages(receiver: String) {
        loggedUser = SessionManager.get(context).getSessionData()!!
        messagingRepository.readContact(
            context,
            loggedUser.uid,
            getContactId(),
            { contactMessages ->

                val list = LinkedList<Chat>()
                contactMessages.forEach { message ->
                    val chat = Chat()
                    if (message.uid == loggedUser.uid) {
                        chat.sender = loggedUser.uid
                        chat.receiver = receiver
                    } else {
                        chat.receiver = loggedUser.uid
                        chat.sender = receiver
                    }
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

    fun getContactId(): String {
        val intent = (context as Activity).intent
        val contactId = intent.getStringExtra("userid")
        return contactId!!
    }

}