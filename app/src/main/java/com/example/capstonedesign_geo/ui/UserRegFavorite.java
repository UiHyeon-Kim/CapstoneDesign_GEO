package com.example.capstonedesign_geo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip checkedChip = group.findViewById(checkedId);
                if (checkedChip != null){
                    // checkedChip.setChipBackgroundColor(getResources().getColor(R.color.mainblue));
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }
            }
        });

        btnNext.setOnClickListener(view -> {
            saveFavoriteTags(); // 선택한 태그들을 저장

            new AlertDialog.Builder(UserRegFavorite.this)
                    .setTitle("가입을 완료 하시겠습니까?")
                    .setMessage("추후 설정에서 변경이 가능합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            Intent intent = new Intent(UserRegFavorite.this, UserRegComplete.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    }).show();

            /*// 선택한 태그들을 저장
            selectedTags.clear(); // 이전에 선택한 태그들을 초기화
            int count = chipGroup.getChildCount(); // 선택한 태그의 개수
            for (int i=0; i<count; i++){
                Chip chip = (Chip) chipGroup.getChildAt(i); // 선택한 태그를 가져옴
                if (chip.isChecked()){ // 선택한 경우
                    selectedTags.add(chip.getText().toString()); // 선택한 태그를 리스트에 추가
                }
            }*/
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

    private void saveFavoriteTags(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> selectedTags = new HashSet<>();

        int count = chipGroup.getChildCount(); // 선택한 태그의 개수
        for (int i=0; i<count; i++){
            Chip chip = (Chip) chipGroup.getChildAt(i); // 선택한 태그를 가져옴
            if (chip.isChecked()){ // 선택한 경우
                selectedTags.add(chip.getText().toString()); // 선택한 태그를 리스트에 추가
            }
        }
        Log.d("UserRegFavorite", "선택한 태그: " + selectedTags);

        editor.putStringSet("favoriteTags", selectedTags);
        editor.apply();
    }
}
