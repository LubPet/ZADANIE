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
import com.example.mobv.adapter.RoomAdapter
import com.example.mobv.R
import com.example.mobv.databinding.FragmentRoomsBinding
import com.example.mobv.interfaces.OnFocusListener
import com.example.mobv.viewModels.RoomsViewModel
import com.example.mobv.viewModels.RoomsViewModelFactory

class RoomsFragment : Fragment(), OnFocusListener {

    private var recyclerView: RecyclerView? = null
    private var roomAdapter: RoomAdapter? = null

    private lateinit var roomsViewModel: RoomsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val binding: FragmentRoomsBinding = FragmentRoomsBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = RoomsViewModelFactory(context!!)
        roomsViewModel = ViewModelProviders.of(this, viewModelFactory).get(RoomsViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.roomsViewModel = roomsViewModel

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView = recyclerView

        roomAdapter = RoomAdapter(context!!, ArrayList())
        recyclerView.adapter = roomAdapter

        roomsViewModel.getRooms().observe(this, Observer { rooms ->
            roomAdapter!!.update(rooms)
        })

        roomsViewModel.readRooms()

        return view
    }

    override fun onFocus() {
        roomsViewModel.readRooms()
    }


}

