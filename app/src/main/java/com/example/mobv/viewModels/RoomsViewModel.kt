package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobv.model.ChatRoom
import com.example.mobv.model.Room
import com.example.mobv.model.factory.MessagingRepositoryFactory
import com.example.mobv.model.repository.AvailableRoomsRepository
import com.example.mobv.session.SessionManager


class RoomsViewModel(val context: Context) : ViewModel() {

    private var availableRoomsRepository = AvailableRoomsRepository.create(context)
    private val messagingRepository = MessagingRepositoryFactory.create()

    private val allRooms : MutableLiveData<List<ChatRoom>> = MutableLiveData()

    fun getRooms(): LiveData<List<ChatRoom>> {
        return allRooms
    }

    fun readRooms() {
        val uid = SessionManager.get(context).getSessionData()!!.uid

        messagingRepository.getRooms(context, uid, { rooms ->

            val foundRooms = ArrayList<ChatRoom>()
            val wifiRooms: List<ChatRoom> = availableRoomsRepository.getAvailableRooms()

            foundRooms.addAll(rooms)
            foundRooms.addAll(wifiRooms)

            var containsPublic = false
            foundRooms.forEach {
                if (it.getName() == Room.public().getName()) {
                    containsPublic = true
                }
            }

            if (!containsPublic) {
                foundRooms.add(Room.public())
            }

            allRooms.value = filter(foundRooms).sortedBy { it.getName() }

        }, {
            it.printStackTrace()
            throw it
        })
    }

    private fun filter(list: List<ChatRoom>): List<ChatRoom> {
        val filtered = ArrayList<ChatRoom>()
        list.forEach { room ->
            if (filtered.none { it.getName() == room.getName()} && room.getName().trim() != "") {
                filtered.add(room)
            }
        }
        return filtered
    }

}