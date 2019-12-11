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
import com.example.mobv.adapter.UserAdapter
import com.example.mobv.R
import com.example.mobv.databinding.FragmentUsersBinding
import com.example.mobv.viewModels.UsersViewModel
import com.example.mobv.viewModels.UsersViewModelFactory

class UsersFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var userAdapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val binding: FragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModelFactory = UsersViewModelFactory(context!!)
        val usersViewModel = ViewModelProviders.of(this, viewModelFactory).get(UsersViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersViewModel = usersViewModel

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView = recyclerView

        usersViewModel.getUsers().observe(this, Observer { users ->
            userAdapter = UserAdapter(context!!, users)
            recyclerView.adapter = userAdapter
        })

        usersViewModel.readUsers()

        return view
    }
}