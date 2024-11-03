package com.example.capstonedesign_geo.viewmodel

import android.content.Context
import android.util.Log
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
    private val context: Context // ChatViewModel이 GeoDatabase에 접근하기 위해 Context 사용, chatViewModel 생성 시 Context 전달
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
                val response = chat.sendMessage(userMessage)

                // 특정 조건에서만 로컬 데이터베이스에서 데이터를 가져오기
                val shouldIncludeLocalData = userMessage.contains("장소") || userMessage.contains("정보")
                val localData = if (shouldIncludeLocalData) fetchLocalData() else emptyList()

                response.text?.let { modelResponse ->
                    val cleanedResponse = modelResponse.trim()
                    val finalResponse = if (localData.isNotEmpty()) {
                        "$cleanedResponse\n\n추가 정보:\n${localData.joinToString("\n")}"
                    } else {
                        cleanedResponse ?: "죄송해요, 정보를 찾을 수 없어요."
                    }

                    // 최종 응답 메시지를 UI 상태에 추가
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
                        text = e.localizedMessage ?: "오류가 발생했습니다.",
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
}
