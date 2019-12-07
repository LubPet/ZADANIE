package com.example.mobv

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Adapter.MessageAdapter
import com.example.mobv.Model.Chat
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.session.SessionManager
import java.util.*

class MessageActivity : AppCompatActivity() {

    var messageAdapter: MessageAdapter? = null

    var recyclerView: RecyclerView? = null

    private var loggedUser : LoggedUser = LoggedUser()

    private val messagingRepository = MessagingRepositoryFactory.create()

    private var messages = LinkedList<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggedUser = SessionManager.get(this).getSessionData()!!

        setContentView(R.layout.activity_message)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        this.recyclerView = recyclerView

        val btnSend = findViewById<ImageButton>(R.id.btn_send)
        val textSend = findViewById<EditText>(R.id.text_send)

        btnSend.setOnClickListener {
            val msg = textSend.text.toString()
            if (msg != "") {
                val contactId = getContactId()
                sendMessage(loggedUser.uid, contactId, msg)
            } else {
                Toast.makeText(this@MessageActivity, "Prázdna správa", Toast.LENGTH_SHORT).show()
            }

            textSend.setText("")
        }

        readMessages(loggedUser.uid, getContactId())
    }

    private fun sendMessage(sender: String, receiver: String, message: String) {
        messagingRepository.messageContact(this@MessageActivity, sender, receiver, message, {
            messages.add(Chat(sender, receiver, message))

        }, {
            it.printStackTrace()
            Toast.makeText(this@MessageActivity, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })
    }

    private fun readMessages(uid: String, receiver: String) {
        messagingRepository.readContact(this@MessageActivity, uid, getContactId(), { contactMessages ->
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
                messages.add(chat)
            }

            messageAdapter = MessageAdapter(this@MessageActivity, messages)
            recyclerView!!.adapter = messageAdapter

        }, {
            it.printStackTrace()
            Toast.makeText(this@MessageActivity, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getContactId(): String {
        val contactId = intent.getStringExtra("userid")
        return contactId!!
    }
}