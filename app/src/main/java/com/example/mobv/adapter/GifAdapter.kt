package com.example.mobv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobv.model.GifResource
import com.example.mobv.R
import com.example.mobv.databinding.GifItemBinding

class GifAdapter(
    private val mContext: Context,
    private var gifs: List<GifResource>,
    private val listener: GifListener

) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val gif = gifs[position]
        holder.bind(gif, listener)

        Glide.with(mContext)
            .load(gif.images.fixed_height.url)
            .into(holder.gif)

    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    fun update(gifs: List<GifResource>) {
        this.gifs = gifs
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: GifItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var gif: ImageView = itemView.findViewById(R.id.gif)

        fun bind(item: GifResource, listener: GifListener) {
            binding.gifResourceModel = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GifItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    class GifListener(val listener: (gif: GifResource) -> Unit) {

        fun onClick(gif: GifResource) = listener(gif)

    }

}