package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegUserType extends AppCompatActivity {

    private Button btnNext, btnDis, btnNoneDis; //장애인, 비장애인, 확인 버튼 변수
    private UserRepository userRepository; //Repository안에 저장하는 클래스명

    private void saveUsertype(boolean ut){
        //문자를 적었을 때, 페이지 이동을 해도 남아있다. 이걸 안 하면 계속 재저장해야함
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("user_type",ut);
        editor.apply();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_usertype);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnNext = findViewById(R.id.btnNext);
        btnDis = findViewById(R.id.btnDis);
        btnNoneDis = findViewById(R.id.btnNoneDis);
        userRepository = new UserRepository(this); //클래스에 저장할 객체

        //UserRegUserType 페이지에서 UserRegAge 페이지로 옮긴다는 뜻
        btnNext.setOnClickListener(Intent intent = new Intent(UserRegUserType.this, UserRegAge.class));
        startActivity(intent); //intent 객체가 가지고 있는 걸 startActivity가 실행하는 함수

        btnNoneDis.setOnClickListener(view -> {
            saveUsertype(true);
            btnNoneDis.setVisibility(View.VISIBLE);
            btnDis.setVisibility(View.INVISIBLE);
        });

        btnDis.setOnClickListener(view -> {
            saveUsertype(false);
            btnNoneDis.setVisibility(View.INVISIBLE);
            btnDis.setVisibility(View.VISIBLE);
        });

    }
}
