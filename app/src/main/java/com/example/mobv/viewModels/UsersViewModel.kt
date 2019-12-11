package com.example.mobv.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobv.model.factory.MessagingRepositoryFactory
import com.example.mobv.api.responses.Contact
import com.example.mobv.session.SessionManager


class UsersViewModel(val context: Context) : ViewModel() {

    private val messagingRepository = MessagingRepositoryFactory.create()

    private val allUsers : MutableLiveData<List<Contact>> = MutableLiveData()

    fun getUsers(): LiveData<List<Contact>> {
        return allUsers
    }


    fun readUsers() {
        val uid = SessionManager.get(context).getSessionData()!!.uid

        messagingRepository.getContacts(context, uid, { contacts ->

            val list = ArrayList<Contact>()

            contacts.forEach {
                list.add(it)
            }
            allUsers.postValue(list)

        }, {
            it.printStackTrace()
            throw it
        })
    }

}