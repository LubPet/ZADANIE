package com.example.mobv.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobv.MessageActivity;
import com.example.mobv.Model.ChatRoom;
import com.example.mobv.R;
import com.example.mobv.api.responses.Contact;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context mContext;
    private List<ChatRoom> chatRooms;

    public RoomAdapter(Context mContext, List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_item, parent, false);

        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChatRoom room = chatRooms.get(position);
        holder.roomName.setText(room.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("roomid", room.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView roomName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.roomName);
        }
    }

}
