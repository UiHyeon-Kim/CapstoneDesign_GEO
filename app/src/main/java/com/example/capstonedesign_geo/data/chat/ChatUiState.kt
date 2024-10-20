package com.example.capstonedesign_geo.data.chat

data class ChatUiState(val messages: List<ChatMessage>) {

    // copy: 채팅 UI 상태를 복사하여 새로운 상태를 생성하는 함수
    fun copy(): ChatUiState {
        return ChatUiState(messages.toList())           // 새로운 리스트를 생성하여 복사
    }

    // addMessage: 새로운 메시지를 UI 상태에 추가하는 함수
    fun addMessage(message: ChatMessage): ChatUiState {
        val updatedMessages = messages.plus(message)    // 메시지를 기존 리스트에 추가
        return copy(updatedMessages)                    // 메시지 추가된 새로운 상태 반환
    }

    // replaceLastPendingMessage: 마지막으로 추가된 대기 중인 메시지를 대체하는 함수
    fun replaceLastPendingMessage(): ChatUiState {
        val updatedMessages = messages.toMutableList()  // 새로운 리스트 생성
        val lastIndex = updatedMessages.lastIndex       // 마지막 인덱스에 메시지 추가
        val lastMessage = updatedMessages.getOrNull(lastIndex) ?: return this // 마지막 메시지 가져오기

        if (lastMessage.isPending) {                    // 마지막 메시지가 대기 중인 경우
            updatedMessages[lastIndex] = lastMessage.copy(isPending = false)
        }
        return copy(updatedMessages.toList())           // 새로운 상태 반환
    }
}