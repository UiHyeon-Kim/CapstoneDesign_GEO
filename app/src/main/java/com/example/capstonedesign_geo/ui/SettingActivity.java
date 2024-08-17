package com.example.capstonedesign_geo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("설정");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.mainblue));*/
    }
}
