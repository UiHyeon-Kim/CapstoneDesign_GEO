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
import java.util.Calendar;
import java.util.List;

public class UserRegAge extends AppCompatActivity {

    private Button btnNext;
    private Spinner yearSpinner;
    private CustomSpinnerAdapter adapter;
    private int age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_age);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        yearSpinner = findViewById(R.id.yearSpinner);
        btnNext = findViewById(R.id.btnNext);

        // 연도를 저장할 리스트 생성
        List<String> years = new ArrayList<>();
        years.add(getString(R.string.yearSelect)); // 기본값 설정
        int currentYear = Calendar.getInstance().get(Calendar.YEAR); // 현재 연도 가져오기
        for (int i = currentYear; i >= 1950; i--) { // 1950년부터 현재 연도까지 저장
            years.add(String.valueOf(i));
        }

        // 커스텀 어댑터 초기화
        adapter = new CustomSpinnerAdapter(this, years);
        yearSpinner.setAdapter(adapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override // 매개변수 1.선택한 어댑터 뷰 2.선택한 뷰 3.선택한 항목의 인덱스 4.선택한 항목의 아이디
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 항목을 선택했을 때
                age = adapter.getCount();
                if (position == 0) { // 기본값인 경우
                    btnNext.setEnabled(false);
                } else { // 기본값이 아닌 경우
                    btnNext.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { /* 아무 항목도 선택하지 않았을 때 */}
        });

        btnNext.setOnClickListener(view -> {
            String selectedYear = yearSpinner.getSelectedItem().toString();
            int age = currentYear - Integer.parseInt(selectedYear); // 현재 연도에서 선택한 연도를 빼서 나이 계산
            Log.d("UserRegAge", "나이: " + age);
            saveAge(age);
            Intent intent = new Intent(UserRegAge.this, UserRegLocation.class);
            startActivity(intent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void saveAge(int age) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("age", age);
        editor.apply();
    }
}
