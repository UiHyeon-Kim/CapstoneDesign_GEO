package com.example.capstonedesign_geo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstonedesign_geo.R
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
            val response = processUserMessage(userMessage)
            _uiState.value.replaceLastPendingMessage()

            _uiState.value.addMessage(
                ChatMessage(
                    text = response,
                    participant = Participant.MODEL,
                    isPending = false
                )
            )
            /*try {
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
            }*/
        }
    }

    // 사용자의 메시지를 처리하고 로컬 데이터베이스에서 장소 정보를 검색
    private suspend fun processUserMessage(message: String): String = withContext(Dispatchers.IO) {
        val allPlace = naverMapDataDao.getAll()
        val aftermessage = extractKeywordsFromMessage(message)

        // 메시지와 일치하는 장소를 필터링합니다.
        val matchPlaces = allPlace.filter { place ->
            aftermessage.any { keyword ->
                place.title.contains(keyword, ignoreCase = true) ||
                        place.category.contains(keyword, ignoreCase = true) ||
                        place.addr1.contains(keyword, ignoreCase = true) ||
                        place.addr2.contains(keyword, ignoreCase = true) ||
                        place.content.contains(keyword, ignoreCase = true) ||
                        place.amenity.contains(keyword, ignoreCase = true)
            }
        }

        // 일치하는 장소가 있으면 최대 5개까지만 반환합니다.
        if (matchPlaces.isNotEmpty()) {
            return@withContext matchPlaces.take(5)
                .joinToString("\n") { "${it.title} - ${it.addr1}" }
        } else {
            return@withContext "죄송해요, '${aftermessage}'에 대한 정보를 찾을 수 없어요."
        }
    }

    fun extractKeywordsFromMessage(message: String): List<String> {
        // 조사나 불필요한 내용 제거
        val particles = context.getString(R.string.postposition).split(", ").map { it.trim() }

        // 아래 문자 단위로 문자열을 분리
        val words = message.split(" ", "에 대해", "알려줘", "는", "가", "영업시간")

        // 불필요한 단어를 제거한 후, 빈 문자열이 아닌 단어들만 반환합니다.
        return words.map { it.trim() }
            .filter { it.isNotEmpty() && !particles.contains(it) }
    }
}
