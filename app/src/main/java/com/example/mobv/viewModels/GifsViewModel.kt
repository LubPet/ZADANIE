package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobv.Adapter.RoomAdapter
import com.example.mobv.Adapter.UserAdapter
import com.example.mobv.Model.ChatRoom
import com.example.mobv.Model.GifResource
import com.example.mobv.Model.Room
import com.example.mobv.Model.User
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.Model.giphy.repository.GiphyRepository
import com.example.mobv.Model.repository.AvailableRoomsRepository
import com.example.mobv.api.responses.Contact
import com.example.mobv.session.SessionManager


class GifsViewModel(val context: Context) : ViewModel() {

    private var gifs : MutableLiveData<List<GifResource>> = MutableLiveData()

    private val giphyRepository = GiphyRepository.create(context)

    fun getGifs(): LiveData<List<GifResource>> {
        return gifs
    }

    fun showGifs() {
        giphyRepository.search("test", 5, {
            gifs.postValue(it)
        }, {
            it.printStackTrace()
        })
    }

}