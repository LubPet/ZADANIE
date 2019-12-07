package com.example.mobv.Model

import android.content.Context
import com.example.mobv.api.ZadanieApi
import com.example.mobv.api.requests.ListContactRequest
import com.example.mobv.api.responses.Contact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MessagingRepository: IMessagingRepository {

    override fun getContacts(ctx: Context, uid: String, onSuccess: (List<Contact>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call: Call<List<Contact>> = ZadanieApi.create(ctx).listContacts(ListContactRequest(uid))

        fetch(call, onSuccess, onFailure)
    }

    private fun <T> fetch(call: Call<T>, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        return call.enqueue(object: Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == 200) {
                    onSuccess(response.body()!!)

                } else {
                    onFailure(Exception("Failed to get data " + response.code() + " " + response.message() + " " + response.body()))
                }
            }
        })
    }

}