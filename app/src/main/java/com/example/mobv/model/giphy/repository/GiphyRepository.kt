package com.example.mobv.model.giphy.repository

import android.content.Context
import com.example.mobv.model.GifResource
import com.example.mobv.model.repository.CallHandler
import com.example.mobv.api.GiphyApi
import com.example.mobv.api.responses.GiphyResponseData
import retrofit2.Call

class GiphyRepository(private val ctx: Context) : CallHandler() {

    private val giphyApi: GiphyApi = GiphyApi.create(ctx)

    fun search(query: String, limitTo: Int, onSuccess: (List<GifResource>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call : Call<GiphyResponseData> = giphyApi.search(GiphyApi.API_KEY, query, limitTo)

        fetch(call, {
            onSuccess(it.data)
        }, onFailure)
    }

    fun getURL(gifId: String): String {
        var id = gifId
        if (gifId.startsWith("gif:"))
            id = gifId.replace("gif:", "")
        return "https://media2.giphy.com/media/$id/200w.gif"
    }

    companion object {

        fun create(ctx: Context): GiphyRepository {
            return GiphyRepository(ctx)
        }
    }

}