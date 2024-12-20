package com.example.capstonedesign_geo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegUserType extends AppCompatActivity {

    private Button btnNext, btnDis, btnNoneDis; //장애인, 비장애인, 확인 버튼 변수
    private ImageButton backButton;
    private TextView tvNickname;
    private String nickname;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_usertype);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnNext = findViewById(R.id.btnNext);
        btnDis = findViewById(R.id.btnDis);
        btnNoneDis = findViewById(R.id.btnNoneDis);
        backButton = findViewById(R.id.backButton);
        tvNickname = findViewById(R.id.tvNickname);

        getUserNickname();
        tvNickname.setText(nickname + "님의 유형을 선택해주세요 !");

        //UserRegUserType 페이지에서 UserRegAge 페이지로 옮긴다는 뜻
        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegUserType.this, UserRegAge.class);
            startActivity(intent); //intent 객체가 가지고 있는 걸 startActivity가 실행하는 함수
        });

        btnNoneDis.setOnClickListener(view -> { // 장애인이 아닌 경우
            saveUsertype(false);
            btnNoneDis.setBackgroundResource(R.drawable.rounded_button);
            btnDis.setBackgroundResource(R.drawable.rounded_disable);
            btnNext.setEnabled(true);
        });

        btnDis.setOnClickListener(view -> { // 장애인인 경우
            saveUsertype(true);
            btnNoneDis.setBackgroundResource(R.drawable.rounded_disable);
            btnDis.setBackgroundResource(R.drawable.rounded_button);
            btnNext.setEnabled(true);
        });

        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void saveUsertype(boolean ut) {
        //문자를 적었을 때, 페이지 이동을 해도 남아있다. 이걸 안 하면 계속 재저장해야함
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("user_type", ut);
        editor.apply();
        Log.d("UserRegUserType", "유저타입: " + ut);
    }

    // 사용자의 닉네임을 불러오는 함수
    private void getUserNickname() {
        SharedPreferences prefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nickname = prefs.getString("nickname", null);
    }
}