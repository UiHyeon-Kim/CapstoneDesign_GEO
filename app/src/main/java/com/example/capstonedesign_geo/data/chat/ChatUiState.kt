package com.example.capstonedesign_geo.data.chat

class ChatUiState(private val _messages: MutableList<ChatMessage>) {
    val messages: List<ChatMessage> = _messages

    fun addMessage(msg: ChatMessage) {
        _messages.add(msg)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull() ?: return
        if (lastMessage.isPending) {
            _messages[_messages.size - 1] = lastMessage.copy(isPending = false)
        }
    }
}