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
import com.example.mobv.databinding.ActivityLoginBinding
import com.example.mobv.databinding.ActivityMessageBinding
import com.example.mobv.databinding.ActivityRegisterBinding
import com.example.mobv.session.SessionManager
import com.example.mobv.viewModels.*
import kotlinx.android.synthetic.main.activity_room_message.*
import java.util.*

class RoomMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_message)
        setSupportActionBar(toolbar)
    }
}