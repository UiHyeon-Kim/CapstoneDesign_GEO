package com.example.capstonedesign_geo.data.chat

enum class Participant {
    USER, MODEL, ERROR
}

data class ChatMessage(
    var text: String = "", // 메시지 내용
    val participant: Participant = Participant.USER, // 메시지 발신자
    val isPending: Boolean = false // 메시지 처리 상태
)