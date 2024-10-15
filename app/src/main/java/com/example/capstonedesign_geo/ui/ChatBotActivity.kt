package com.example.capstonedesign_geo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign_geo.databinding.ActivityChatbotBinding
import com.example.capstonedesign_geo.ui.adapter.ChatAdapter
import com.example.capstonedesign_geo.viewmodel.ChatViewModel
import com.example.capstonedesign_geo.viewmodel.GenerativeViewModelFactory

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 초기화
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, GenerativeViewModelFactory).get(ChatViewModel::class.java)

        // 리사이클러뷰 초기화
        chatAdapter = ChatAdapter(emptyList())
        binding.chatRecyclerView.adapter = chatAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.uiState.observe(this) { uiState ->
            chatAdapter = ChatAdapter(uiState.messages)
            binding.chatRecyclerView.adapter = chatAdapter
            binding.chatRecyclerView.smoothScrollToPosition(0)
        }

        // 메시지 전송 버튼 클릭 리스너 설정
        binding.buttonSend.setOnClickListener {
            val userMessage = binding.messageInput.text.toString()
            if (userMessage.isNotBlank()) {
                viewModel.sendMessage(userMessage)
                binding.messageInput.text.clear()
                binding.chatRecyclerView.smoothScrollToPosition(0)
            }
        }
    }
}