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

import com.example.mobv.adapter.GifAdapter
import com.example.mobv.R
import com.example.mobv.databinding.FragmentGifsBinding
import com.example.mobv.viewModels.GifsViewModel
import com.example.mobv.viewModels.GifsViewModelFactory


class GifFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private lateinit var gifsViewModel: GifsViewModel

    private lateinit var gifAdapter: GifAdapter

    private var searchString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGifsBinding = FragmentGifsBinding.inflate(inflater, container, false)

        val viewModelFactory = GifsViewModelFactory(context!!)
        gifsViewModel = ViewModelProviders.of(this, viewModelFactory).get(GifsViewModel::class.java)

        binding.searchQuery = searchString
        binding.gifsViewModel = gifsViewModel
        val view = binding.root

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        gifsViewModel.getGifs().observe(this, Observer { gifs ->
            gifAdapter = GifAdapter(context!!, gifs, GifAdapter.GifListener {

            })
            recyclerView!!.adapter = gifAdapter
        })

        if (searchString.isNotBlank()) gifsViewModel.searchGifs(searchString)
        return view
    }

}
