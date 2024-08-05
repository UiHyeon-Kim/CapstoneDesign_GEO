package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserRegFavorite extends AppCompatActivity {

    private Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegFavorite.this, UserRegComplete.class);
            startActivity(intent);
        });
    }
}
