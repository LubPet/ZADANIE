package com.example.mobv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.adapter.MessageAdapter
import com.example.mobv.R
import com.example.mobv.databinding.FragmentRoomMessageBinding
import com.example.mobv.viewModels.RoomMessageViewModel
import com.example.mobv.viewModels.RoomMessageViewModelFactory

class RoomMessageFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var messageAdapter: MessageAdapter? = null

    private lateinit var roomMessageViewModel: RoomMessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val binding: FragmentRoomMessageBinding = FragmentRoomMessageBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = RoomMessageViewModelFactory(context!!)
        roomMessageViewModel = ViewModelProviders.of(this, viewModelFactory).get(RoomMessageViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.roomMessageViewModel = roomMessageViewModel

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView = recyclerView

        roomMessageViewModel.getMessages().observe(this, Observer { messages ->
            messageAdapter = MessageAdapter(context!!, messages)
            recyclerView.adapter = messageAdapter
        })
        roomMessageViewModel.messageContent = view.findViewById(R.id.messageContent)

        roomMessageViewModel.readMessages()


        return view
    }

}