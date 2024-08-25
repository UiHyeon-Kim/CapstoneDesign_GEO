package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

// 스플래시 화면(앱 진입 시 보여지는 로딩 화면)
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.transparent));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // 앱의 다크모드 비활성화

        new Handler(Looper.getMainLooper()).postDelayed(() -> { // 1.5초 후에 실행
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // 애니메이션 제거
            startActivity(intent);
            finish();
        }, 1500); // 1.5초 딜레이
    }
}
