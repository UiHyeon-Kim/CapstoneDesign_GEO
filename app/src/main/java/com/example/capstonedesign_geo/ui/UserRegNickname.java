package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.Model.UserInfo;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.UUID;

public class UserRegNickname extends AppCompatActivity {

    private EditText editNickname;
    private Button btnNext;
    private UserRepository userRepository;
    private String androidId;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_nickname);
        // 상태바 색 변경
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        // 뷰와 db 초기화
        editNickname = findViewById(R.id.editNickname);
        btnNext = findViewById(R.id.btnNext);
        userRepository = new UserRepository(this);

        // 기기 일련번호 가져오기
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // 사용자 ID 생성 (UUID)
        userId = UUID.randomUUID().toString();

        // 확인 버튼 이벤트 리스너
        btnNext.setOnClickListener(view -> {
            String nickname = editNickname.getText().toString();
            saveNickname(nickname); // 닉네임 저장

            Intent intent = new Intent(UserRegNickname.this, UserRegUsertype.class);
            startActivity(intent);
        });

        // EditText 입력에 따른 처리를 하기 위한 이벤트 리스너
        editNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnNext.setEnabled(s.length() > 1);
            }
        });
    }
    // editText에 적어둔 텍스트 저장해둠
    private void saveNickname(String nickname){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nickname", nickname);
        editor.putString("androidId", androidId);
        editor.putString("userId", userId);
        editor.apply();

        // SQLite 데이터베이스에 닉네임 저장
        UserInfo userInfo = new UserInfo(userId, androidId, nickname, false,0,null,false, null);
        userRepository.saveUser(userInfo);
    }
}
