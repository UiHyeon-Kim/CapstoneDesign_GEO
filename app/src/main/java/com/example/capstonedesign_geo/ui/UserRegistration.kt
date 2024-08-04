package com.example.capstonedesign_geo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.databinding.UserRegistrationBinding
import com.example.capstonedesign_geo.utility.setStatusBarColor

class UserRegistration : AppCompatActivity() {
    private lateinit var binding: UserRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 상태바 색상 변경
        setStatusBarColor(ContextCompat.getColor(this, R.color.transparent))
        // 코틀린 이벤트 리스너 - 인텐트
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, UserRegNickname::class.java)
            startActivity(intent)
        }

    }
}