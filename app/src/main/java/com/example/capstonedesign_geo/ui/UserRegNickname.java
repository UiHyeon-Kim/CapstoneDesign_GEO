package com.example.capstonedesign_geo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegNickname extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_nickname);
        // 상태바 색 변경
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));
    }
}
