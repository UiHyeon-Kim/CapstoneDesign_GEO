package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashSet;
import java.util.Set;

public class UserRegFavorite extends AppCompatActivity implements ConfirmDialogInterface {

    private Button btnNext;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_favorite);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        chipGroup = findViewById(R.id.chipGroup);
        btnNext = findViewById(R.id.btnNext);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip checkedChip = group.findViewById(checkedId);
                // 선택한 경우
                btnNext.setEnabled(checkedChip != null);
            }
        });

        btnNext.setOnClickListener(view -> {
            saveFavoriteTags(); // 선택한 태그들을 저장

            CustomDialog dialog = CustomDialog.newInstance("가입을 완료 하시겠습니까?", "추후 설정에서 변경이 가능합니다.");
            dialog.setConfirmDialogInterface(this);
            dialog.show(getFragmentManager(), "CustomDialog");

        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onConfirmClick(@NonNull String item) {
        Intent intent = new Intent(this, UserRegComplete.class);
        startActivity(intent);
    }

    private void saveFavoriteTags() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> selectedTags = new HashSet<>();

        int count = chipGroup.getChildCount(); // 선택한 태그의 개수
        for (int i = 0; i < count; i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i); // 선택한 태그를 가져옴
            if (chip.isChecked()) { // 선택한 경우
                selectedTags.add(chip.getText().toString()); // 선택한 태그를 리스트에 추가
            }
        }
        Log.d("UserRegFavorite", "선택한 태그: " + selectedTags);
        editor.putStringSet("favoriteTags", selectedTags);
        editor.apply();
    }
}
