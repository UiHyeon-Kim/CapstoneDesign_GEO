package com.example.capstonedesign_geo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign_geo.data.chat.ChatMessage
import com.example.capstonedesign_geo.data.chat.ChatUiState
import com.example.capstonedesign_geo.data.chat.Participant
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel(private val generativeModel: GenerativeModel) : ViewModel() {

    private val _uiState = MutableLiveData(ChatUiState(emptyList()))
    val uiState: LiveData<ChatUiState> = _uiState

    init {
        initializeChat()
    }

    private fun initializeChat() {
        val chatHistory = mutableListOf(
            ChatMessage(
                text = "안녕하세요! 저는 GEO라고해요. 무엇을 도와드릴까요?",
                participant = Participant.USER
            )
        )
        _uiState.value = ChatUiState(chatHistory)
    }

    fun sendMessage(userMessage: String) {
        val currentState = _uiState.value ?: return
        val newMessage = ChatMessage(
            userMessage,
            Participant.USER,
            true
        )
        currentState.addMessage(newMessage)
        _uiState.value = currentState

        viewModelScope.launch {
            try {
                val response = generativeModel.sendMessage(userMessage).text
                currentState.replaceLastPendingMessage()
                currentState.addMessage(ChatMessage(response, Participant.MODEL))
                _uiState.postValue(currentState)
            } catch (e: Exception) {
                currentState.replaceLastPendingMessage()
                currentState.addMessage(ChatMessage(e.localizedMessage, Participant.ERROR))
                _uiState.postValue(currentState)
            }
        }
    }
}