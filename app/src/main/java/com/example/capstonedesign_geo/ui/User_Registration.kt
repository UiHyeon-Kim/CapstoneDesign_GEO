package com.example.capstonedesign_geo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.utility.setStatusBarColor

class User_Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)

        // 상태바 색상 변경
        setStatusBarColor(ContextCompat.getColor(this, R.color.transparent))
    }
}