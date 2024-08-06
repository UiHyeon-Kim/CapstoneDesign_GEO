package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserRegAge extends AppCompatActivity {

    private Button btnNext;
    private Spinner yearSpinner;
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
        int currentYear = Calendar.getInstance().get(Calendar.YEAR); // 현재 연도 가져오기
        for (int i = currentYear; i >= 1950; i--) {
            years.add(String.valueOf(i));
        }

        // 배열 어댑터를 사용해 연도 리스트를 Spinner에 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        // 드롭다운 리스트의 레이아웃 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter); // Spinner에 어댑터 적용

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 항목을 선택했을 때
                btnNext.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { /* 아무 항목도 선택하지 않았을 때 */}
        });

        btnNext.setOnClickListener(view -> {
            String selectedYear = yearSpinner.getSelectedItem().toString();
            int age = currentYear - Integer.parseInt(selectedYear);
            saveAge(age);

            Intent intent = new Intent(UserRegAge.this, UserRegLocation.class);
            startActivity(intent);
        });
    }

    private void saveAge(int age) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("age", age);
        editor.apply();
    }
}
