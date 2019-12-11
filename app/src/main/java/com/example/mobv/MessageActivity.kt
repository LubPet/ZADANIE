package com.example.mobv

import android.os.Bundle
import android.view.View
import android.widget.EditText
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


class MessageActivity : AppCompatActivity() {

    private var messageAdapter: MessageAdapter? = null

    var recyclerView: RecyclerView? = null

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
        messageViewModel.messages = MutableLiveData()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        binding.recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView!!.layoutManager = linearLayoutManager
        this.recyclerView = recyclerView

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
}
