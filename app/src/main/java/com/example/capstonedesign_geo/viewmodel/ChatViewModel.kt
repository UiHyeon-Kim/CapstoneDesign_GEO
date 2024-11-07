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
            val searchList = matchPlaces.take(5)
                .joinToString(separator = "\n\n", prefix = "\n\n") {
                    "\n\n${it.title}\n 주소: ${it.addr1}\n 전화번호: ${it.tel}\n 컨텐츠: ${it.content}"
                }
            val responseTemplete = randomAnswerFormat()
            return@withContext responseTemplete.format(searchList)

        } else {
            return@withContext randomEmptyAnswerFormat(aftermessage)
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

    fun randomAnswerFormat(): String {
        return listOf(
            "여기 찾으신 장소입니다! %s",
            "이런 장소들을 찾았어요! %s",
            "아래 장소들이 도움이 될 것 같아요! %s",
            "이 장소들 어떠세요? %s",
            "이런 곳은 어떠세요? %s",
            "이곳들은 어떠신가요? %s",
            "다음과 같은 장소들이 있어요! %s",
            "다양한 선택지를 준비했어요! %s",
            "좀 더 구체적인 정보를 드릴게요! %s",
            "검색 결과를 보여드릴게요! %s",
            "궁금해하시던 곳이죠? %s",
            "짜잔! 여기 있습니다! %s",
            "여기보세요!! %s",
            "원하시는 곳이 여기 있네요! %s",
            "이곳들을 추천해 드립니다. %s",
            "관련된 장소 목록을 확인해 보세요! %s",
            "아래 목록에서 선택해 보세요! %s",
            "아래의 장소들을 확인해 보세요! %s"
        ).random()
    }

    fun randomEmptyAnswerFormat(message: List<String>): String {
        return listOf(
            "죄송해요, '${message.joinToString(", ")}'에 대한 정보를 찾을 수 없어요.ㅜㅜ",
            "죄송해요, 다시 검색해 보시겠어요?\n\n",
            "죄송해요, 원하는 장소를 찾지 못했어요.\n\n",
            "죄송해요, 관련된 장소를 찾지 못했어요.\n\n",
            "죄송해요, 검색 결과가 없어요. 다른 검색어를 시도해 주세요.\n\n",
            "죄송해요, 요청하신 장소를 찾지 못했어요.\n\n",
            "죄송해요, 더 나은 검색어를 사용해 보시겠어요?\n\n",
            "죄송해요, 원하는 결과가 보이지 않네요.\n\n",
            "죄송해요, 아직 관련된 장소를 찾지 못했어요.\n\n",
            "죄송해요, 입력 정보를 다시 확인해 주세요.\n\n",
            "죄송해요, 장소를 찾을 수 없어 도움을 드리지 못했네요.\n\n",
            "죄송합니다, 검색 결과가 없습니다. \n\n",
            "죄송합니다, 찾으시는 장소를 발견하지 못했습니다.\n\n",
            "죄송합니다, 찾으시는 정보를 제공할 수 없습니다.\n\n",
            "죄송합니다, 입력한 장소가 존재하지 않습니다.\n\n",
            "죄송합니다, 입력한 정보로는 장소를 찾을 수 없습니다.\n\n",
            "죄송합니다, 관련된 데이터를 찾지 못했습니다.\n\n",
            "죄송합니다, 장소를 찾지 못해 유감입니다.\n\n",
            "죄송합니다, 검색 결과가 비어 있습니다.\n\n",
            "죄송합니다, 해당 장소에 대한 정보가 없습니다.\n\n",
            "죄송합니다, 관련 장소가 존재하지 않습니다.\n\n",
            "죄송합니다, 장소를 찾을 수 없어 안타깝습니다.\n\n",
            "죄송합니다, 입력한 검색어로는 아무것도 찾을 수 없습니다.\n\n",
            "죄송합니다, 이 검색어에 맞는 장소가 없습니다.\n\n",
            "죄송합니다, 이 검색어에 맞는 장소가 없습니다.\n\n",
            "죄송합니다, 현재 검색된 정보가 없습니다.\n\n"
        ).random()
    }
}
