package com.example.mobv.Fragments;

import android.app.Activity;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Adapter.RoomAdapter
import com.example.mobv.Adapter.UserAdapter
import com.example.mobv.Model.ChatRoom
import com.example.mobv.Model.Room
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.Model.repository.AvailableRoomsRepository
import com.example.mobv.R
import com.example.mobv.session.SessionManager

class RoomsFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var roomAdapter: RoomAdapter? = null

    private var availableRoomsRepository : AvailableRoomsRepository? = null
    private val messagingRepository = MessagingRepositoryFactory.create()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView = recyclerView

        availableRoomsRepository = AvailableRoomsRepository.create(context!!)
        readRooms()
        return view
    }

    private fun readRooms() {
        val uid = SessionManager.get(context!!).getSessionData()!!.uid

        messagingRepository.getRooms(context!!, uid, { rooms ->

            val allRooms = ArrayList<ChatRoom>()
            val wifiRooms : List<ChatRoom> = availableRoomsRepository!!.getAvailableRooms()

            allRooms.addAll(rooms)
            allRooms.addAll(wifiRooms)

            var containsPublic = false
            allRooms.forEach {
                if (it.getName() == Room.public().getName()) {
                    containsPublic = true
                }
            }

            if (!containsPublic) {
                allRooms.add(Room.public())
            }

            roomAdapter = RoomAdapter(context!!, allRooms)
            recyclerView!!.adapter = roomAdapter

        }, {
            it.printStackTrace()
            throw it
        })

    }
}
