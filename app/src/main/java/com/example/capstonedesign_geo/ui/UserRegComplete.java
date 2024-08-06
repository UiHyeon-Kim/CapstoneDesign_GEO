package com.example.capstonedesign_geo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class UserRegComplete extends AppCompatActivity {

    private Button btnComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_complete);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        btnComplete = findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegComplete.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveUserInfo() {
        SharedPreferences sp = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

    }
}
