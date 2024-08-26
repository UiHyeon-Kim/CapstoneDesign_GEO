package com.example.capstonedesign_geo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    List<ChatMessage> chatMessageList;
    public ChatAdapter(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        if (chatMessage.getSender().equals("me")) {
            holder.geoImg.setVisibility(View.GONE);
            holder.geoName.setVisibility(View.GONE);
            holder.botMessage.setVisibility(View.GONE);
            holder.userMessage.setVisibility(View.VISIBLE);
            holder.userMessage.setText(chatMessage.getMessage());
        } else {
            holder.geoImg.setVisibility(View.VISIBLE);
            holder.geoName.setVisibility(View.VISIBLE);
            holder.userMessage.setVisibility(View.GONE);
            holder.botMessage.setVisibility(View.VISIBLE);
            holder.botMessage.setText(chatMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView geoImg;
        TextView geoName;
        TextView botMessage;
        TextView userMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            geoImg = itemView.findViewById(R.id.geoImg);
            geoName = itemView.findViewById(R.id.geoName);
            botMessage = itemView.findViewById(R.id.botMessage);
            userMessage = itemView.findViewById(R.id.userMessage);
        }
    }
}
