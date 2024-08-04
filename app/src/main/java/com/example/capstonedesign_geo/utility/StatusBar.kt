package com.example.capstonedesign_geo.utility

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat

// 상태바 색 변경 함수
fun Activity.setStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = color

        // 상태바 아이콘 색상 변경
        if (color == ContextCompat.getColor(this, android.R.color.transparent)){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = 0
        }
    }
}