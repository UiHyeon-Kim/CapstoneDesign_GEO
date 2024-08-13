package com.example.capstonedesign_geo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.BottomSheet;
import com.example.capstonedesign_geo.ui.fragment.NaverFragment;


public class MainActivity extends AppCompatActivity {

    BottomSheet bottomsheet;
    Button menubutton;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId") // 에러 무시
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

        //네이버지도 fragment 인스턴스 적용
        NaverFragment naverFragment = new NaverFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_zone, naverFragment).commit();

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
        /*if (isUserPreferencesComplete()) {  // 사용자 선호조 조사가 완료된 경우
            setContentView(R.layout.activity_main); // 메인 화면으로 이동
        } else {    // 사용자 선호도 조사가 완료되지 않은 경우 첫 번째 화면으로 이동
            Intent intent = new Intent(MainActivity.this, UserRegistration.class);
            startActivity(intent);
            overridePendingTransition(0,0); // 액티비티 전환시 애니메이션 삭제
            finish(); // 현재 액티비티 종료
        }*/
    }
    // SharedPreferences에 사용자 데이터가 모두 저장되어 있는지 확인
    private boolean isUserPreferencesComplete() {
        SharedPreferences sharedPreferences =getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.contains("nickname") && sharedPreferences.contains("userType") &&
                sharedPreferences.contains("age") && sharedPreferences.contains("location") &&
                sharedPreferences.contains("hasPet") && sharedPreferences.contains("favoriteTags");
    }
}

