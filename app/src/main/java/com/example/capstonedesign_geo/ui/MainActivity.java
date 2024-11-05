package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.example.capstonedesign_geo.ui.fragment.BottomSheet;
import com.example.capstonedesign_geo.ui.fragment.NaverFragment;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;
import com.example.capstonedesign_geo.ui.fragment.NaverMapDataDao;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private BottomSheet bottomsheet;
    private EditText editSearch;
    private long backPressedTime; // 마지막 뒤로가기 누른 시간
    private Toast backToast; // 뒤로가기 메시지
    private FloatingActionButton floatingActionButton;

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
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // 앱의 다크모드 비활성화

        if (isUserPreferencesComplete()) {  // 사용자 선호조 조사가 완료된 경우
            setContentView(R.layout.activity_main); // 메인 화면으로 이동
        } else {    // 사용자 선호도 조사가 완료되지 않은 경우 첫 번째 화면으로 이동
            Intent intent = new Intent(MainActivity.this, UserRegistration.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // 액티비티 전환시 애니메이션 삭제
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
        editSearch = findViewById(R.id.editSearch);
        floatingActionButton = findViewById(R.id.btn_chatbot);

        // 검색 창 터치 이벤트
        editSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable[] drawables = editSearch.getCompoundDrawables();
                Drawable drawableLeft = drawables[0];
                Drawable drawableRight = drawables[2];

                // 터치된 위치의 X 좌표 가져오기
                float x = event.getX();

                // 추가 터치 영역 정의
                int extraTouchAreaDp = 20;
                float density = getResources().getDisplayMetrics().density;
                int extraTouchAreaPx = (int) (extraTouchAreaDp * density + 0.5f);

                // 왼쪽 아이콘 터치 영역
                if (drawableLeft != null) {
                    int drawableLeftEnd = editSearch.getPaddingLeft() + drawableLeft.getIntrinsicWidth();
                    if (x >= (editSearch.getPaddingLeft() - extraTouchAreaPx) && x <= (drawableLeftEnd + extraTouchAreaPx)) {
                        bottomsheet = new BottomSheet();
                        bottomsheet.show(getSupportFragmentManager(), bottomsheet.getTag());
                        return true;
                    }
                }

                // 오른쪽 아이콘 클릭
                if (drawableRight != null) {
                    int drawableRightStart = editSearch.getWidth() - editSearch.getPaddingRight() - drawableRight.getIntrinsicWidth();
                    if (x >= (drawableRightStart - extraTouchAreaPx) && x <= (editSearch.getWidth() + extraTouchAreaPx)) {

                        // 입력된 텍스트 가져오기
                        String searchText = editSearch.getText().toString().trim();

                        if (!searchText.isEmpty()) {
                            // Room 데이터베이스 인스턴스 가져오기
                            GeoDatabase db = GeoDatabase.getInstance(MainActivity.this);
                            NaverMapDataDao dao = db.naverMapDataDao();

                            // 검색 결과 가져오기
                            new Thread(() -> {
                                List<NaverMapData> searchResults = dao.searchByText("%" + searchText + "%");

                                // 검색 결과를 PlaceListActivity로 전달
                                Intent intent = new Intent(MainActivity.this, PlaceListActivity.class);
                                intent.putParcelableArrayListExtra("searchResults", new ArrayList<>(searchResults));
                                startActivity(intent);
                            }).start();
                        }
                    }
                }
            }
            return false;
        });

        // 임시 챗봇 버튼 클릭 이벤트
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatBotActivity.class);
            startActivity(intent);
        });
    }

    // SharedPreferences에 사용자 데이터가 모두 저장되어 있는지 확인
    private boolean isUserPreferencesComplete() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // SharedPreferences에서 저장된 사용자 데이터 가져오기
        String userId = sharedPreferences.getString("userId", null);
        String androidId = sharedPreferences.getString("androidId", null); // 가져올 값과 기본값
        String nickname = sharedPreferences.getString("nickname", null);
        boolean userType = sharedPreferences.getBoolean("userType", false);
        int age = sharedPreferences.getInt("age", 0);
        String location = sharedPreferences.getString("location", null);
        Set<String> favoriteTags = sharedPreferences.getStringSet("favoriteTags", null);

        Log.d("MainActivity", "userId: " + userId);
        Log.d("MainActivity", "androidId: " + androidId);
        Log.d("MainActivity", "nickname: " + nickname);
        Log.d("MainActivity", "userType: " + userType);
        Log.d("MainActivity", "age: " + age);
        Log.d("MainActivity", "location: " + location);
        Log.d("MainActivity", "favoriteTags: " + favoriteTags);

        // 아래 변수들의 값이 저장되어 있으면 선호도 조사 완료
        return androidId != null && nickname != null && location != null && age > 0;// && favoriteTags != null;
    }

    // 뒤로가기 버튼 지연 시간
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) { // 2초 이내에 뒤로가기 버튼을 눌렀을 경우
            if (backToast != null) backToast.cancel(); // 이전 Toast가 있으면 취소
            super.onBackPressed(); // 앱 종료
            return;
        } else { // 첫 번째로 뒤로 가기 버튼을 누른 경우
            backToast = Toast.makeText(this, "종료하시려면 한 번 더 눌러주세요", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis(); // 현재 시간을 저장
    }

    // 프래그먼트에 chip 카테고리 전달
    private void sendCategotyToFragment(String category) {
        NaverFragment naverFragment = (NaverFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_zone);

        if (naverFragment != null) {
            // naverFragment.함수명(category);
        }
    }

}

