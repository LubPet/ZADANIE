package com.example.mobv.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.model.ChatRoom
import com.example.mobv.R
import com.example.mobv.RoomMessageActivity

class RoomAdapter(
    private val mContext: Context,
    private val chatRooms: List<ChatRoom>

) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.room_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val room = chatRooms[position]
        holder.roomName.text = room.getName()

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, RoomMessageActivity::class.java)
            intent.putExtra("room", room)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var roomName: TextView = itemView.findViewById(R.id.roomName)

    }

}