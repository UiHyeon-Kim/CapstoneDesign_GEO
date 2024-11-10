package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.adapter.CustomSpinnerAdapter;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRegLocation extends AppCompatActivity {

    private Button btnNext;
    private Spinner citySpinner;
    private Spinner districtSpinner;
    private CustomSpinnerAdapter cityAdapter;
    private CustomSpinnerAdapter districtAdapter;
    private String select;
    private Map<String, List<String>> cityToDistrictMap;
    private String city;
    private String district;
    private String location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_location);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        citySpinner = findViewById(R.id.citySpinner);
        districtSpinner = findViewById(R.id.districtSpinner);
        btnNext = findViewById(R.id.btnNext);

        select = getString(R.string.locationSelect); // 거주지를 선택하세요 문자열 저장

        // 도시를 저장할 리스트 생성 및 저장
        List<String> cities = new ArrayList<>();
        cities.add(select); // 거주지 선택 문자열을 맨 첫 줄에 추가
        cities.addAll(List.of("서울", "경기", "인천", "강원", "충북", "충남", "경북", "경남", "전북", "전남", "대전", "대구", "광주", "부산", "울산", "세종", "제주"));

        // 도시별 구를 저장할 Map 생성 및 저장
        cityToDistrictMap = new HashMap<>();
        cityToDistrictMap.put(select, new ArrayList<>(List.of(select))); // 기본값 설정 (선택하세요 문자열)
        // 각 도시별 구를 매핑해서 저장
        cityToDistrictMap.put("서울", new ArrayList<>(List.of(select, "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구")));
        cityToDistrictMap.put("경기", new ArrayList<>(List.of(select, "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시")));
        cityToDistrictMap.put("인천", new ArrayList<>(List.of(select, "강화군", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구")));
        cityToDistrictMap.put("강원", new ArrayList<>(List.of(select, "강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군")));
        cityToDistrictMap.put("충북", new ArrayList<>(List.of(select, "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "증평군", "진천군", "청주시", "충주시", "추풍령")));
        cityToDistrictMap.put("충남", new ArrayList<>(List.of(select, "계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군", "천안시", "청양군", "태안군", "홍성군")));
        cityToDistrictMap.put("경북", new ArrayList<>(List.of(select, "경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시", "영천시")));
        cityToDistrictMap.put("경남", new ArrayList<>(List.of(select, "거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군", "창원시", "통영시", "하동군", "함안군", "함양군", "합천군")));
        cityToDistrictMap.put("전북", new ArrayList<>(List.of(select, "고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군")));
        cityToDistrictMap.put("전남", new ArrayList<>(List.of(select, "강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군")));
        cityToDistrictMap.put("대전", new ArrayList<>(List.of(select, "대덕구", "동구", "서구", "유성구", "중구")));
        cityToDistrictMap.put("대구", new ArrayList<>(List.of(select, "남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구")));
        cityToDistrictMap.put("광주", new ArrayList<>(List.of(select, "광산구", "남구", "동구", "북구", "서구")));
        cityToDistrictMap.put("부산", new ArrayList<>(List.of(select, "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구")));
        cityToDistrictMap.put("울산", new ArrayList<>(List.of(select, "남구", "동구", "북구", "울주군", "중구")));
        cityToDistrictMap.put("세종", new ArrayList<>(List.of(select, "세종시")));
        cityToDistrictMap.put("제주", new ArrayList<>(List.of(select, "서귀포시", "제주시", "남제주군", "북제주군")));

        cityAdapter = new CustomSpinnerAdapter(this, cities); // 커스텀 어댑터 초기화
        citySpinner.setAdapter(cityAdapter); // 스피너에 어댑터 설정

        districtAdapter = new CustomSpinnerAdapter(this, new ArrayList<>());
        districtSpinner.setAdapter(districtAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override // 매개변수 1.선택한 어댑터 뷰 2.선택한 뷰 3.선택한 항목의 인덱스 4.선택한 항목의 아이디
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String City = (String) parent.getItemAtPosition(position); // 어댑터에서 선택한 인덱스의 항목을 가져옴
                if (!select.equals(City)) { // 기본값이 아닌 경우
                    List<String> districts = cityToDistrictMap.get(City); // 해당 도시의 구를 가져옴
                    if (districts != null) { // 해당 도시의 구가 있는 경우
                        districtAdapter.updateData(districts); // 구 어댑터 업데이트
                    }
                } else { // 기본값인 경우
                    districtAdapter.updateData(new ArrayList<>(List.of(select)));
                }
                city = City; // 선택한 도시 저장
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String District = (String) parent.getItemAtPosition(position);
                // 기본값인 경우
                // 기본값이 아닌 경우
                btnNext.setEnabled(!District.equals(select));
                district = District;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                btnNext.setEnabled(false);
            }
        });

        btnNext.setOnClickListener(view -> {
            saveLocation(city, district);

            Intent intent = new Intent(UserRegLocation.this, UserRegFavorite.class);
            startActivity(intent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void saveLocation(String city, String district) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", city + " " + district);
        editor.apply();
        Log.d("UserRegLocation", "도시: " + city + ", 구: " + district);
    }
}
