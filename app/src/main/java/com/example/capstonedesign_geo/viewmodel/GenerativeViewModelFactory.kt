package com.example.capstonedesign_geo.viewmodel

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonedesign_geo.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig


class GenerativeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val config = generationConfig {
            temperature = 0.7f // 텍스트 생성의 다양성을 조절하는 온도 값
        }
        return when {
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash-latest", // 사용할 모델 이름
                    apiKey = BuildConfig.GEMINIAPI,
                    generationConfig = config
                )
                // Context를 전달하여 ChatViewModel 생성
                ChatViewModel(generativeModel, context)
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}

