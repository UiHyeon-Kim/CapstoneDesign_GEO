package com.example.capstonedesign_geo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.data.chat.ChatMessage
import com.example.capstonedesign_geo.databinding.ItemChatMessageBinding

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding: ItemChatMessageBinding): RecyclerView.ViewHolder(binding.root)

    // onCreateViewHolder: 뷰홀더를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    // onBindViewHolder: 뷰홀더에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.apply {
            messageText.text = message.text
        }
    }

    override fun getItemCount(): Int = messages.size
}