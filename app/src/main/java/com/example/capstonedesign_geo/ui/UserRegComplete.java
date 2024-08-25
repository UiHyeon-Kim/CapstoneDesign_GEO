package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.model.UserInfo;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.HashSet;
import java.util.Set;

public class UserRegComplete extends AppCompatActivity {

    private Button btnComplete;
    private TextView greeting;
    private UserRepository userRepository;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_complete);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnComplete = findViewById(R.id.btnComplete);
        greeting = findViewById(R.id.tvGreeting);
        userRepository = new UserRepository(this);

        // 설문조사 완료 환영 문구 출력
        getNickname();
        greeting.setText(nickname + getString(R.string.RegisterComplete1));

        // 모든 사용자 정보 한 번에 저장
        saveUserInfo();

        btnComplete.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegComplete.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // SharedPreferences로 저장한 UserPreferences의 사용자 정보 가져오기
        String userId = sharedPreferences.getString("userId", null);
        String androidId = sharedPreferences.getString("androidId", null);
        String nickname = sharedPreferences.getString("nickname", null);
        boolean userType = sharedPreferences.getBoolean("userType", false);
        int age = sharedPreferences.getInt("age", 0);
        String location = sharedPreferences.getString("location", null);
        Set<String> favoriteTags = sharedPreferences.getStringSet("favoriteTags", new HashSet<>());

        UserInfo userInfo = new UserInfo(userId, androidId, nickname, userType, age, location, favoriteTags);

        userRepository.saveUser(userInfo);
    }

    private void getNickname() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("nickname", null);
    }
}
