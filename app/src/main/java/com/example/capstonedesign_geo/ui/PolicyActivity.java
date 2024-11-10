package com.example.capstonedesign_geo.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class PolicyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }
}
