package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.Model.UserInfo;
import com.example.capstonedesign_geo.data.repository.UserRepository;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.UUID;

public class UserRegNickname extends AppCompatActivity {

    private EditText editNickname;
    private TextView nicknameFeedback;
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
        nicknameFeedback = findViewById(R.id.nicknameFeedback);
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

            Intent intent = new Intent(UserRegNickname.this, UserRegUserType.class);
            startActivity(intent);
        });

        // 뒤로가기 버튼 이벤트 리스너
        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());

        // EditText 입력에 따른 처리를 하기 위한 이벤트 리스너
        editNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                // 텍스트가 변경되기 전의 상태 처리
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                // 텍스트가 변경되는 동안의 상태 처리
                if (s == null || s.length() == 0){  // 비밀번호가 입력되기 시작하면
                    nicknameFeedback.setVisibility(View.GONE);
                } else {
                    nicknameFeedback.setVisibility(View.VISIBLE);
                    passNickname(s.toString(), nicknameFeedback);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트가 변경된 후의 상태 처리
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

    private void passNickname(String nickname, TextView nicknameFeedback) {
        int maxLen = 20;
        int minLen = 2;
        String hangulNotAvailable = ".*[ㄱ-ㅎㅏ-ㅣ\\s]+.*";

        if (nickname.length() > maxLen) {
            nicknameFeedback.setText("닉네임이 너무 길어요!");
            nicknameFeedback.setTextColor(ContextCompat.getColor(this, R.color.warning));
            btnNext.setEnabled(false);
        } else if (nickname.length() < minLen) {
            nicknameFeedback.setText("닉네임이 너무 짧아요!");
            nicknameFeedback.setTextColor(ContextCompat.getColor(this, R.color.warning));
            btnNext.setEnabled(false);
        } else if (nickname.matches(hangulNotAvailable)) {
            nicknameFeedback.setText("사용 불가능한 글자가 있어요!");
            nicknameFeedback.setTextColor(ContextCompat.getColor(this, R.color.warning));
            btnNext.setEnabled(false);
        } else {
            nicknameFeedback.setText("사용 가능한 닉네임이에요!");
            nicknameFeedback.setTextColor(ContextCompat.getColor(this, R.color.pass));
            btnNext.setEnabled(true);
        }
    }
}
