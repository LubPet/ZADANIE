package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobv.model.GifResource
import com.example.mobv.model.giphy.repository.GiphyRepository


class GifsViewModel(val context: Context) : ViewModel() {

    private var gifs : MutableLiveData<List<GifResource>> = MutableLiveData()

    private val giphyRepository = GiphyRepository.create(context)

    fun getGifs(): LiveData<List<GifResource>> {
        return gifs
    }

    fun searchGifs(query: String) {
        giphyRepository.search(query, 20, {
            gifs.postValue(it)

        }, {
            it.printStackTrace()
        })
    }

}