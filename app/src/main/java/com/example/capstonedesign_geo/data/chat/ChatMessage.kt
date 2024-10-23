package com.example.capstonedesign_geo.data.chat

enum class Participant { // 메시지 발신자를 나타내는 열거형 클래스
    USER, MODEL, ERROR
}

data class ChatMessage( // 채팅 메시지를 나타내는 데이터 클래스
    var text: String = "", // 메시지 내용
    val participant: Participant = Participant.USER, // 메시지 발신자
    val isPending: Boolean = false // 메시지 처리 상태
)