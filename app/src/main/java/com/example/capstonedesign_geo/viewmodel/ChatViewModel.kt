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

    // UI 상태를 나타내는 LiveData
    private val _uiState = MutableLiveData(ChatUiState(emptyList()))
    // UI 상태를 읽기 전용 LiveData
    val uiState: LiveData<ChatUiState> = _uiState

    // 채팅 초기화
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

    // 사용자 메시지를 전송하고 응답을 처리하는 함수
    fun sendMessage(userMessage: String) {
        val currentState = _uiState.value ?: return // 현재 상태 가져오기
        val updatedState = currentState.copy()
        val newMessage = ChatMessage( // 새로운 메시지 생성
            userMessage,
            Participant.USER,
            true
        )
        updatedState.addMessage(newMessage) // 상태에 새로운 메시지 추가
        _uiState.postValue(updatedState) // UI 상태 업데이트

        // 사용자 메시지를 모델에 전송하고 응답을 처리하는 코루틴 실행
        viewModelScope.launch {
            try {
                // 모델에 사용자 메시지를 전송하고 응답을 가져옴
                val response = generativeModel.startChat().sendMessage(userMessage).text
                // 응답이 있는 경우 UI 상태 업데이트
                val updatedStateAfterResponse = updatedState.copy()
                // 마지막 메시지를 대기 중인 상태로 설정하고 응답 메시지를 추가
                updatedStateAfterResponse.replaceLastPendingMessage()
                // 응답 메시지를 추가하여 새로운 상태 생성
                updatedStateAfterResponse.addMessage(ChatMessage(response ?: "", Participant.MODEL))
                // UI 상태 업데이트
                _uiState.postValue(updatedStateAfterResponse)
            } catch (e: Exception) {
                val updatedStateAfterError = updatedState.copy()
                updatedStateAfterError.replaceLastPendingMessage()
                updatedStateAfterError.addMessage(ChatMessage(e.localizedMessage, Participant.ERROR))
                _uiState.postValue(updatedStateAfterError)
            }
        }
    }
}