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

import com.bumptech.glide.Glide;
import com.example.mobv.MessageActivity;
import com.example.mobv.Model.GifResource;
import com.example.mobv.R;
import com.example.mobv.api.responses.Contact;

import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

    private Context mContext;
    private List<GifResource> gifs;

    public GifAdapter(Context mContext, List<GifResource> mGifs) {
        this.gifs = mGifs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gif_item, parent, false);

        return new GifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GifResource user = gifs.get(position);

//        val imageView = findViewById<ImageView>(R.id.webView)
//        Glide.with(this).load("https://media0.giphy.com/media/65ODCwM00NVmEyLsX3/giphy.gif?cid=3f233d15475808b0a42b36e882b128e979e49f69b505ffa9&rid=giphy.gif").into(imageView)

        Glide.with(mContext).load("https://media0.giphy.com/media/65ODCwM00NVmEyLsX3/giphy.gif?cid=3f233d15475808b0a42b36e882b128e979e49f69b505ffa9&rid=giphy.gif").into(holder.gif);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Postnut gif
            }
        });

    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView gif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gif = itemView.findViewById(R.id.gif);
        }
    }

}
