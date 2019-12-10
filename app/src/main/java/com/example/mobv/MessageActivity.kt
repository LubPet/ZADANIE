package com.example.mobv

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Adapter.MessageAdapter
import com.example.mobv.Model.Chat
import com.example.mobv.Model.LoggedUser
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.databinding.ActivityMessageBinding
import com.example.mobv.databinding.ActivityRegisterBinding
import com.example.mobv.session.SessionManager
import com.example.mobv.viewModels.MessageViewModel
import com.example.mobv.viewModels.MessageViewModelFactory
import com.example.mobv.viewModels.RegisterViewModel
import com.example.mobv.viewModels.RegisterViewModelFactory
import java.util.*

class MessageActivity : AppCompatActivity() {

    var messageAdapter: MessageAdapter? = null

    var recyclerView: RecyclerView? = null

    private var loggedUser : LoggedUser = LoggedUser()

    private val messagingRepository = MessagingRepositoryFactory.create()

    private var messages = LinkedList<Chat>()


    private val viewModelFactory = MessageViewModelFactory(this)
    private val messageViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggedUser = SessionManager.get(this).getSessionData()!!

        messageAdapter = MessageAdapter(this, messages)

        val binding: ActivityMessageBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_message
        )
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.messageViewModel = messageViewModel
        messageViewModel.messages = MutableLiveData()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        binding.recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView!!.layoutManager = linearLayoutManager
        this.recyclerView = recyclerView
        messageViewModel.recyclerView = this.recyclerView

//        binding.messageViewModel.messages!!.observe(this, androidx.lifecycle.Observer {
//            messageAdapter = MessageAdapter(this, messages)
//            recyclerView.adapter = messageAdapter
//        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        var messageContent = findViewById<EditText>(R.id.messageContent)
        messageViewModel.messageContent = messageContent

        messageViewModel.readMessages(loggedUser.uid, messageViewModel.getContactId())
    }
}
