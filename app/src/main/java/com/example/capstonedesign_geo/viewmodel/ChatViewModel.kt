package com.example.capstonedesign_geo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign_geo.data.chat.ChatMessage
import com.example.capstonedesign_geo.data.chat.ChatUiState
import com.example.capstonedesign_geo.data.chat.Participant
import com.example.capstonedesign_geo.data.db.GeoDatabase
import com.example.capstonedesign_geo.ui.fragment.NaverMapDataDao
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    generativeModel: GenerativeModel,
) : ViewModel() {

    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "model") { text("안녕하세요 지오에요!\n궁금하신게 있으신가요?") }
        )
    )

    // Room 데이터베이스 인스턴스 가져오기
    private val geoDatabase: GeoDatabase = GeoDatabase.getInstance(context)
    private val naverMapDataDao: NaverMapDataDao = geoDatabase.naverMapDataDao()

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.history.map { content ->
            // 초기 메시지 매핑
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))
    val uiState: StateFlow<ChatUiState> =
        _uiState.asStateFlow()

    //사용자 메시지 전송
    fun sendMessage(userMessage: String) {
        // 사용자 메시지 추가
        _uiState.value.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        // 모델 응답 생성 및 로컬 데이터베이스 사용
        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userMessage) // 사용자 메시지를 모델에 전달
                _uiState.value.replaceLastPendingMessage()


                // 로컬 데이터베이스에서 데이터를 가져와서 응답에 포함
                val localData = processUserMessage(userMessage)
                response.text?.let { modelResponse ->

                    val finalResponse = if (localData.isNotEmpty()) {
                        "$modelResponse\n\n추가 정보:\n${localData}"
                    } else {
                        modelResponse
                    }

                    _uiState.value.addMessage(
                        ChatMessage(
                            text = finalResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.localizedMessage,
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }

    // 로컬 데이터베이스에서 정보 가져오기
    private suspend fun fetchLocalData(): List<String> = withContext(Dispatchers.IO) {
        val dataList = naverMapDataDao.getAll()
        // 필요한 데이터 형식으로 변환 (예: 제목과 주소만 가져오기)
        dataList.map { "${it.title} - ${it.addr1}" }
    }

    private suspend fun processUserMessage(message: String): String = withContext(Dispatchers.IO) {
        // 키워드 목록 정의
        val keywords = listOf("식당", "카페", "공원", "쇼핑몰", "관광지")
        val matchedKeyword = keywords.find { message.contains(it) }

        if (matchedKeyword != null) {
            // 키워드와 일치하는 장소를 데이터베이스에서 검색
            val searchQuery = "%$matchedKeyword%" // LIKE 쿼리를 위한 검색어
            val places = naverMapDataDao.searchByText(searchQuery)

            if (places.isNotEmpty()) {
                // 장소 정보를 문자열로 변환하여 반환
                return@withContext places.joinToString("\n") { "${it.title} - ${it.addr1}" }
            } else {
                // 키워드에 해당하는 장소가 없는 경우
                return@withContext "죄송해요, ${matchedKeyword}에 대한 정보를 찾을 수 없어요."
            }
        } else {
            // 키워드가 메시지에 없는 경우
            return@withContext "죄송해요, 무슨 장소를 찾고 있는지 이해하지 못했어요. 다시 말씀해 주세요."
        }
    }

}
