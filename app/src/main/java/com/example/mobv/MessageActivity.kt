package com.example.mobv

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.adapter.MessageAdapter
import com.example.mobv.model.LoggedUser
import com.example.mobv.databinding.ActivityMessageBinding
import com.example.mobv.session.SessionManager
import com.example.mobv.viewModels.MessageViewModel
import com.example.mobv.viewModels.MessageViewModelFactory
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.mobv.interfaces.OnFragmentDataListener
import com.example.mobv.model.GifResource


class MessageActivity : AppCompatActivity(), OnFragmentDataListener<GifResource> {

    private var messageAdapter: MessageAdapter? = null

    lateinit var recyclerView: RecyclerView

    private var loggedUser : LoggedUser = LoggedUser()

    private val viewModelFactory = MessageViewModelFactory(this)
    private val messageViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggedUser = SessionManager.get(this).getSessionData()!!

        val binding: ActivityMessageBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_message
        )
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        binding.messageViewModel = messageViewModel
        binding.sendMessageListener = OnSendMessageListener {
            messageViewModel.sendMessage(it, {}, {
                Toast.makeText(this@MessageActivity, "Odosielanie zlyhalo", Toast.LENGTH_SHORT).show()
            })
        }
        messageViewModel.messages = MutableLiveData()

        recyclerView = findViewById(R.id.recycler_view)
        messageViewModel.recyclerView = this.recyclerView

        binding.recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = ""
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        findViewById<AppCompatImageButton>(R.id.gifButton).setOnClickListener {
            toggleGifSelection()
        }

        messageViewModel.getMessages().observe(this, Observer { messages ->
            if (messageAdapter != null) {
                messageAdapter!!.setChats(messages)
            } else {
                messageAdapter = MessageAdapter(this@MessageActivity, messages)
            }
            recyclerView.adapter = messageAdapter
        })
        binding.contact = messageViewModel.getContact().name

        val messageContent = findViewById<EditText>(R.id.messageContent)
        messageViewModel.messageContent = messageContent

        messageViewModel.readMessages()
    }

    override fun onResume() {
        super.onResume()
        messageViewModel.readMessages()
    }

    private fun toggleGifSelection() {
        val view = findViewById<ConstraintLayout>(R.id.gifWindow)!!

        if (!view.isVisible) {
            showView(view)
        } else {
            hideView(view)
        }
    }

    private fun showView(view: View) {
        view.visibility = ConstraintLayout.VISIBLE
    }

    private fun hideView(view: View) {
        view.visibility = ConstraintLayout.GONE

    }

    override fun onFragmentData(data: GifResource) {
        try {
            messageViewModel.sendGifMessage(data.id, {}, {
                Toast.makeText(this@MessageActivity, "Odosielanie zlyhalo", Toast.LENGTH_SHORT)
                    .show()
            })

        } catch (e: IllegalArgumentException) {
            Toast.makeText(this@MessageActivity, "Nie ste pripojený k WiFi danej miestnosti", Toast.LENGTH_LONG)
                .show()
        }
    }

    class OnSendMessageListener(val callback: (String) -> Unit) {

        fun sendMessage(message: String) {
            callback(message)
        }

    }
}
