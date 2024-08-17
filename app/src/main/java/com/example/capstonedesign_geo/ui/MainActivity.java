package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.BottomSheet;
import com.example.capstonedesign_geo.ui.fragment.NaverFragment;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private BottomSheet bottomsheet;
    private Button menubutton;
    private long backPressedTime; // 마지막 뒤로가기 누른 시간
    private Toast backToast; // 뒤로가기 메시지

    // @SuppressLint("MissingInflatedId") // 에러 무시
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 시스템 UI(네비, 상태 바 등)이 콘텐츠에 겹치지 않게 함
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets; // UI 요소가 시스템 UI에 가려지지 않게 함
        });
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.transparent));

        // 앱의 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //mainmenu bottomsheet
        menubutton = findViewById(R.id.menubutton);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheet = new BottomSheet();
                bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
            }
        });

        // *********** 메인 액티비티 보고싶으면 아래 if문만 주석 처리 할 것 ************
        if (isUserPreferencesComplete()) {  // 사용자 선호조 조사가 완료된 경우
            setContentView(R.layout.activity_main); // 메인 화면으로 이동
        } else {    // 사용자 선호도 조사가 완료되지 않은 경우 첫 번째 화면으로 이동
            Intent intent = new Intent(MainActivity.this, UserRegistration.class);
            startActivity(intent);
            overridePendingTransition(0,0); // 액티비티 전환시 애니메이션 삭제
            finish(); // 현재 액티비티 종료
        }

        //네이버지도 fragment 인스턴스 적용
        NaverFragment naverFragment = new NaverFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_zone, naverFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mainmenu bottomsheet
        menubutton = findViewById(R.id.menubutton);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheet = new BottomSheet();
                bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
            }
        });
    }

    // SharedPreferences에 사용자 데이터가 모두 저장되어 있는지 확인
    private boolean isUserPreferencesComplete() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // SharedPreferences에서 저장된 사용자 데이터 가져오기
        String androidId = sharedPreferences.getString("androidId", null); // 가져올 값과 기본값
        String nickname = sharedPreferences.getString("nickname", null);
        boolean userType = sharedPreferences.getBoolean("userType", false);
        int age = sharedPreferences.getInt("age", 0);
        String location = sharedPreferences.getString("location", null);
        Set<String> favoriteTags = sharedPreferences.getStringSet("favoriteTags", null);

        Log.d("MainActivity", "androidId: " + androidId);
        Log.d("MainActivity", "nickname: " + nickname);
        Log.d("MainActivity", "userType: " + userType);
        Log.d("MainActivity", "age: " + age);
        Log.d("MainActivity", "location: " + location);
        Log.d("MainActivity", "favoriteTags: " + favoriteTags);

        // 아래 변수들의 값이 저장되어 있으면 선호도 조사 완료
        return androidId != null && nickname != null && location != null && age > 0 && favoriteTags != null;
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){ // 2초 이내에 뒤로가기 버튼을 눌렀을 경우
            if (backToast != null) backToast.cancel(); // 이전 Toast가 있으면 취소
            super.onBackPressed(); // 앱 종료
            return;
        } else { // 첫 번째로 뒤로 가기 버튼을 누른 경우
            backToast = Toast.makeText(this, "종료하시려면 한 번 더 눌러주세요", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis(); // 현재 시간을 저장
    }
}

