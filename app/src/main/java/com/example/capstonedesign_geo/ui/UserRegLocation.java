package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegLocation extends AppCompatActivity {

    private Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_location);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegLocation.this, UserRegHaspet.class);
            startActivity(intent);
        });
    }
}
