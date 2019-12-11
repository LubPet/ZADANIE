package com.example.mobv.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.MessageActivity
import com.example.mobv.Model.Chat
import com.example.mobv.R
import com.example.mobv.RoomMessageActivity
import com.example.mobv.api.responses.Contact
import com.example.mobv.session.SessionManager.Companion.get
import com.example.mobv.utils.DateUtils

class MessageAdapter(
    private val mContext: Context,
    private val messages: List<Chat>
) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return if (viewType == MSG_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val chat = messages[position]
        holder.showMessage.text = chat.message
//        holder.time.text = DateUtils.toString(chat.time!!)
        holder.senderName.text = chat.sender + " (" + DateUtils.toString(chat.time!!) + ")"

        holder.showMessage.setOnClickListener {
            val intent = Intent(mContext, MessageActivity::class.java)
            intent.putExtra("user", Contact("", chat.uid!!))
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val loggedUser =
            get(mContext).getSessionData()
        return if (messages[position].sender == loggedUser!!.uid) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val senderName: TextView = itemView.findViewById(R.id.sender_info)
        val showMessage: TextView = itemView.findViewById(R.id.show_message)

    }

    companion object {
        const val MSG_TYPE_LEFT = 0
        const val MSG_TYPE_RIGHT = 1
    }

}