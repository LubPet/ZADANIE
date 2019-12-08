package com.example.mobv.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.Adapter.UserAdapter
import com.example.mobv.Model.factory.MessagingRepositoryFactory
import com.example.mobv.R
import com.example.mobv.session.SessionManager

class UsersFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var userAdapter: UserAdapter? = null

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

        readUsers()

        return view
    }

    private fun readUsers() {
        val uid = SessionManager.get(context!!).getSessionData()!!.uid

        messagingRepository.getContacts(context!!, uid, { contacts ->

            userAdapter = UserAdapter(context!!, contacts)
            recyclerView!!.adapter = userAdapter

        }, {
            it.printStackTrace()
            throw it
        })

    }
}