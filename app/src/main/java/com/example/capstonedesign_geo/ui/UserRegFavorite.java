package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.google.android.material.chip.Chip;
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
            //new AlertDialog.Builder(UserRegFavorite.this);

            selectedTags.clear();
            int count = chipGroup.getChildCount();
            for (int i=0; i<count; i++){
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()){
                    selectedTags.add(chip.getText().toString());
                }
            }

            Intent intent = new Intent(UserRegFavorite.this, UserRegComplete.class);
            startActivity(intent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void saveFavoriteTags(List<String> tags){
        //SharedPreferences sharedPreferences = SharedPreferences
    }
}
