package com.example.capstonedesign_geo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.data.chat.ChatMessage
import com.example.capstonedesign_geo.databinding.ItemChatMessageBinding

// ChatAdapter: 채팅 메시지를 표시하는 리사이클러뷰 어댑터
class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // ChatViewHolder: 각 메시지 뷰를 보관하는 뷰홀더 클래스
    class ChatViewHolder(val binding: ItemChatMessageBinding): RecyclerView.ViewHolder(binding.root)

    // onCreateViewHolder: 뷰홀더를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        // ItemChatMessageBinding을 사용하여 뷰바인딩을 설정
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 생성된 뷰홀더를 반환
        return ChatViewHolder(binding)
    }

    // onBindViewHolder: 뷰홀더에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]        // 현재 메시지를 가져옴
        holder.binding.apply {                  // 뷰바인딩을 사용하여 각 뷰에 데이터를 설정
            messageText.text = message.text     // 메시지 텍스트 설정
        }
    }

    override fun getItemCount(): Int = messages.size // 메시지 목록의 크기를 반환
}