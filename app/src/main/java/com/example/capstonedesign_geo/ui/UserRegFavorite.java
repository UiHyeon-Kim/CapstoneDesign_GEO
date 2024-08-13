package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class UserRegFavorite extends AppCompatActivity {

    private Button btnNext;
    private ChipGroup chipGroup;
    private List<String> selectedTags;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_favorite);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        chipGroup = findViewById(R.id.chipGroup);
        btnNext = findViewById(R.id.btnNext);
        selectedTags = new ArrayList<>();

        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(UserRegFavorite.this, UserRegComplete.class);
            startActivity(intent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void saveFavoriteTags(List<String> tags){

    }
}
