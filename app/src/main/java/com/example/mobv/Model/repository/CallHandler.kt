package com.example.mobv.Model.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

abstract class CallHandler {


    fun <T> fetch(call: Call<T>, onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        return call.enqueue(callback(onSuccess, onFailure))
    }

    fun <T> send(call: Call<T>, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        return call.enqueue(callbackNullable(onSuccess, onFailure))
    }

    private fun <T> callback(onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit): Callback<T> {
        return object: Callback<T> {

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
        }
    }

    private fun <T> callbackNullable(onSuccess: () -> Unit, onFailure: (Throwable) -> Unit): Callback<T> {
        return object: Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == 200) {
                    onSuccess()

                } else {
                    onFailure(Exception("Failed to get data " + response.code() + " " + response.message() + " " + response.body()))
                }
            }
        }
    }

}
