package com.example.capstonedesign_geo.data.chat

import com.example.capstonedesign_geo.ui.fragment.NaverMapData

enum class Participant { // 메시지 발신자를 나타내는 열거형 클래스
    USER, MODEL, ERROR
}

data class ChatMessage(
    var text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = false,
    val places: List<NaverMapData> = emptyList()
)