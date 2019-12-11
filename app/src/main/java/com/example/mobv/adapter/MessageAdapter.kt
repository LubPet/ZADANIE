package com.example.mobv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobv.MessageActivity
import com.example.mobv.model.Chat
import com.example.mobv.R
import com.example.mobv.api.responses.Contact
import com.example.mobv.session.SessionManager.Companion.get
import com.example.mobv.utils.DateUtils

class MessageAdapter(
    private val mContext: Context,
    private var messages: List<Chat>

) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): ViewHolder {
        var layout: Int? = null
        when(viewType) {
            MSG_TYPE_RIGHT -> layout = R.layout.chat_item_right
            MSG_TYPE_LEFT -> layout = R.layout.chat_item_left
            MSG_TYPE_GIF_RIGHT -> layout = R.layout.chat_item_gif_right
            MSG_TYPE_GIF_LEFT -> layout = R.layout.chat_item_gif_left
        }
        val view = LayoutInflater.from(mContext).inflate(layout!!, parent, false)

        return ViewHolder(view, viewType)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val chat = messages[position]

        var text = chat.sender
        if (chat.time != null) {
            text = text + " (" + DateUtils.toString(chat.time!!) + ")"
        }
        holder.senderName.text = text
        holder.setMessage(chat)

        holder.getMessageView().setOnClickListener {
            val intent = Intent(mContext, MessageActivity::class.java)

            if (chat.uid != null) {
                intent.putExtra("user", Contact("", chat.uid!!))
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        val loggedUser = get(mContext).getSessionData()
        return if (message.sender == loggedUser!!.uid) {

            if (message.isGif) {
                MSG_TYPE_GIF_RIGHT
            } else {
                MSG_TYPE_RIGHT
            }
        } else {
            if (message.isGif) {
                MSG_TYPE_GIF_LEFT
            } else {
                MSG_TYPE_LEFT
            }
        }
    }

    fun setChats(chats: List<Chat>) {
        messages = chats
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val senderName: TextView = itemView.findViewById(R.id.sender_info)

        private var showMessage: TextView? = null
        private var gifMessage: ImageView? = null

        private var chat: Chat? = null

        constructor(itemView: View, viewType: Int) : this(itemView) {
            if (viewType == MSG_TYPE_LEFT || viewType == MSG_TYPE_RIGHT) {
                showMessage = itemView.findViewById(R.id.show_message)
            } else {
                gifMessage = itemView.findViewById(R.id.gif_message)
            }

        }

        fun setMessage(chat: Chat) {
            this.chat = chat
            if (chat.isGif) {
                Glide.with(mContext)
                    .load(chat.gifUrl)
                    .into(gifMessage!!)
            } else {
                showMessage!!.text = chat.message
            }
        }

        fun getMessageView(): View {
            return if (this.chat!!.isGif) {
                gifMessage!!
            } else {
                showMessage!!
            }
        }

    }

    companion object {
        const val MSG_TYPE_LEFT = 0
        const val MSG_TYPE_RIGHT = 1

        const val MSG_TYPE_GIF_LEFT = 2
        const val MSG_TYPE_GIF_RIGHT = 3
    }

}