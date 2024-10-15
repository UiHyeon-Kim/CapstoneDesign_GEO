package com.example.capstonedesign_geo.data.chat

data class ChatUiState(val messages: List<ChatMessage>) {

    // Function to create a copy of the current state
    fun copy(): ChatUiState {
        return ChatUiState(messages.toList()) // Create a new, immutable list
    }

    // Function to add a new message (returns a new state)
    fun addMessage(message: ChatMessage): ChatUiState {
        val updatedMessages = messages.plus(message) // Create a new list with added message
        return copy(updatedMessages) // Return a new state with updated list
    }

    // Function to replace the last pending message (returns a new state)
    fun replaceLastPendingMessage(): ChatUiState {
        val updatedMessages = messages.toMutableList() // Create a mutable copy
        val lastIndex = updatedMessages.lastIndex
        val lastMessage = updatedMessages.getOrNull(lastIndex) ?: return this // Handle empty list

        if (lastMessage.isPending) {
            updatedMessages[lastIndex] = lastMessage.copy(isPending = false)
        }
        return copy(updatedMessages.toList()) // Return a new state with updated list
    }
}