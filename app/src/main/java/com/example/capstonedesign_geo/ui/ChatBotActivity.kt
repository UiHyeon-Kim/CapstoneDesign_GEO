package com.example.capstonedesign_geo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.data.chat.ChatMessage
import com.example.capstonedesign_geo.data.chat.Participant
import com.example.capstonedesign_geo.viewmodel.ChatViewModel
import com.example.capstonedesign_geo.viewmodel.GenerativeViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

class ChatBotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatRoute()
        }
    }
}

// 채팅 루트 컴포저블
@Preview(showBackground = true)
@Composable
internal fun ChatRoute(
    chatViewModel: ChatViewModel = viewModel(factory = GenerativeViewModelFactory)
) {
    val chatUiState by chatViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var showCategoryButtons by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            ChatTopBar()
        },
        bottomBar = {
            MessageInput(
                onSendMessage = { inputText ->
                    chatViewModel.sendMessage(inputText)
                    showCategoryButtons = false
                },
                resetScroll = {
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()
        ) {
            // Messages List
            ChatList(chatUiState.messages, listState)

            if (showCategoryButtons) {
                CategoryButtons(chatViewModel)
            }
        }
    }
}

@Composable
fun CategoryButtons(chatViewModel: ChatViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val categories = listOf("식당", "카페", "공원", "쇼핑", "숙박")
        categories.forEach { category ->
            StyledCategoryButton(text = category, onClick = { chatViewModel.sendMessage(category) })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(onBackClick: () -> Unit = {}) {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(0xFF4F89F8),
            darkIcons = false
        )
    }
    TopAppBar(
        title = { Text(text = "", modifier = Modifier.fillMaxWidth()) },
        navigationIcon = {
            IconButton(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP// or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back_btn),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4F89F8))
    )
}

// 채팅 목록을 표시하는 컴포저블
@Composable
fun ChatList(
    chatMessages: List<ChatMessage>,
    listState: LazyListState
) {
    LazyColumn(
        reverseLayout = true,
        state = listState
    ) {
        items(chatMessages.reversed()) { message ->
            ChatBubbleItem(message)
        }
    }
}

// 채팅 메시지 컴포저블
@Composable
fun ChatBubbleItem(chatMessage: ChatMessage) {
    val isModelMessage = chatMessage.participant == Participant.MODEL ||
            chatMessage.participant == Participant.ERROR

    val context = LocalContext.current
    val markwon = remember(context) { Markwon.create(context) }

    val annotatedString = remember(chatMessage.text) {
        val spannable = markwon.toMarkdown(chatMessage.text)
        AnnotatedString.Builder().apply { append(spannable) }.toAnnotatedString()
    }

    val backgroundColor = if (isModelMessage) {
        Color.Transparent
    } else {
        Color(0xFF4F89F8)
    }

    val textColor = if (isModelMessage) {
        Color.Black
    } else {
        Color.White
    }

    val borderColor = if (isModelMessage) {
        Color(0xFF4F89F8)
    } else {
        Color.Transparent
    }

    val bubbleShape = RoundedCornerShape(20.dp)

    Column(
        horizontalAlignment = if (isModelMessage) Alignment.Start else Alignment.End,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        if (isModelMessage) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_geo),
                    contentDescription = "Geo",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "지오",
                    fontFamily = notoSansFamily,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        BoxWithConstraints {
            Card(
                shape = bubbleShape,
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                modifier = Modifier
                    .drawBehind {
                        drawRoundRect(
                            color = borderColor,
                            size = size,
                            cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                    }
                    .widthIn(0.dp, maxWidth * 0.9f)
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = annotatedString,
                        color = textColor,
                        fontFamily = notoSansFamily,
                        style = TextStyle(fontSize = 16.sp)
                    )

                    chatMessage.places.forEach { place ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    val intent =
                                        Intent(context, PlaceDetailActivity::class.java).apply {
                                            putExtra("title", place.title)
                                            putExtra("addr1", place.addr1)
                                            putExtra("tel", place.tel)
                                            putExtra("content", place.content)
                                            putExtra("firstimage", place.firstimage)
                                            putExtra("category", place.category)
                                            putExtra("amenity", place.amenity)
                                            putExtra("hours", place.hours)
                                        }
                                    context.startActivity(intent)
                                }
                        ) {
                            // 이미지 표시 (Coil 사용)
                            if (!place.firstimage.isNullOrEmpty()) {
                                AsyncImage(
                                    model = place.firstimage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(bottom = 8.dp)
                                )
                            }

                            Text(
                                text = place.title,
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                                fontFamily = notoSansFamily,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "주소: ${place.addr1}",
                                style = TextStyle(fontSize = 16.sp),
                                fontFamily = notoSansFamily,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "전화번호: ${place.tel}",
                                fontFamily = notoSansFamily,
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}


// 메시지 입력 컴포저블
@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    resetScroll: () -> Unit = {}
) {
    var userMessage by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // 메시지 입력 필드
        TextField(
            value = userMessage,
            onValueChange = { userMessage = it },
            placeholder = { Text(text = "메시지를 입력하세요") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .weight(0.85f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Gray
            )
        )
        // 전송 버튼
        IconButton(
            onClick = {
                if (userMessage.isNotBlank()) {
                    onSendMessage(userMessage)
                    userMessage = ""
                    resetScroll()
                }
            },
            modifier = Modifier
                .background(Color(0xFF4F89F8))
                .fillMaxHeight()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.action_send),
                tint = Color.White
            )
        }
    }
}

val notoSansFamily = FontFamily(
    Font(R.font.notosans_bold, FontWeight.Bold),
    Font(R.font.notosans, FontWeight.Normal)
)

@Composable
fun StyledCategoryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4F89F8), // 버튼 배경색 설정
            contentColor = Color.White // 텍스트 색상
        )
    ) {
        Text(text = text)
    }
}