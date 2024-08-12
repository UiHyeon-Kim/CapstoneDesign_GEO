package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.Model.UserInfo;
import com.example.capstonedesign_geo.data.Model.UserInfoTemp;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegComplete extends AppCompatActivity {

    private Button btnComplete;
    private UserRepository userRepository;
    private UserInfoTemp userInfoTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_complete);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnComplete = findViewById(R.id.btnComplete);
        userRepository = new UserRepository(this);
        userInfoTemp = getIntent().getParcelableExtra("userInfoTemp");

        btnComplete.setOnClickListener(view -> {
            UserInfo userInfo = new UserInfo(
                userInfoTemp.getUserId(),
                userInfoTemp.getAndroidId(),
                userInfoTemp.getNickname(),
                userInfoTemp.isUserType(),
                userInfoTemp.getAge(),
                userInfoTemp.getLocation(),
                userInfoTemp.getFavoriteTags()
            );
            userRepository.saveUser(userInfo);

            // saveUserInfo(); // 위 UserInfo 안되면 이것으로 하기

            Intent intent = new Intent(UserRegComplete.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        String userId = sharedPreferences.getString("userId", null);
        String androidId = sharedPreferences.getString("androidId", null);
        String nickname = sharedPreferences.getString("nickname", null);
        boolean userType = sharedPreferences.getBoolean("userType", false);
        int age = sharedPreferences.getInt("age", 0);
        String location = sharedPreferences.getString("location", null);
        String favoriteTags = sharedPreferences.getString("favoriteTags", null);

        UserInfo userInfo = new UserInfo(userId, androidId, nickname, userType, age, location,  favoriteTags);
    }
}
