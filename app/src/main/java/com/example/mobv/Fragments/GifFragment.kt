package com.example.mobv.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.mobv.Adapter.GifAdapter
import com.example.mobv.Adapter.UserAdapter
import com.example.mobv.Model.User
import com.example.mobv.R
import com.example.mobv.viewModels.GifsViewModel
import com.example.mobv.viewModels.GifsViewModelFactory
import com.example.mobv.viewModels.UsersViewModel
import com.example.mobv.viewModels.UsersViewModelFactory


class GifFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private lateinit var gifAdapter: GifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gifs, container, false)

        val viewModelFactory = GifsViewModelFactory(context!!)
        val gifViewModel = ViewModelProviders.of(this, viewModelFactory).get(GifsViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        gifViewModel.getGifs().observe(this, Observer { gifs ->
            gifAdapter = GifAdapter(context!!, gifs)
            recyclerView!!.adapter = gifAdapter
        })

        gifViewModel.showGifs()
        return view
    }

}
