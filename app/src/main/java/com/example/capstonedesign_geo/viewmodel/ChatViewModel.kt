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

    init { initializeChat() }

    private fun initializeChat(){
        val greetings = listOf(
            "안녕하세요! 저는 지오라고 해요.\n무엇을 도와드릴까요?",
            "안녕하세요! 저는 지오예요.\n\'망월사 맛집 찾아줘\'와 같이 질문해 보세요!",
            "안녕하세요! 저는 지오예요.\n무엇을 도와드릴까요?"
        )
        val randomGreeting = greetings.random()
        val chatHistory = mutableListOf(
            ChatMessage(
                text = randomGreeting,
                participant = Participant.USER
            )
        )
        _uiState.value = ChatUiState(chatHistory)
    }

    fun sendMessage(userMessage: String) {
        val currentState = _uiState.value ?: return
        val updatedState = currentState.copy()
        val newMessage = ChatMessage(
            userMessage,
            Participant.USER,
            true
        )
        updatedState.addMessage(newMessage)
        _uiState.postValue(updatedState)

        viewModelScope.launch {
            try {
                val response = generativeModel.startChat().sendMessage(userMessage).text
                val updatedStateAfterResponse = updatedState.copy() // Create another copy
                updatedStateAfterResponse.replaceLastPendingMessage()
                updatedStateAfterResponse.addMessage(ChatMessage(response ?: "", Participant.MODEL))
                _uiState.postValue(updatedStateAfterResponse)
            } catch (e: Exception) {
                val updatedStateAfterError = updatedState.copy() // Create another copy
                updatedStateAfterError.replaceLastPendingMessage()
                updatedStateAfterError.addMessage(ChatMessage(e.localizedMessage, Participant.ERROR))
                _uiState.postValue(updatedStateAfterError)
            }
        }
    }
}