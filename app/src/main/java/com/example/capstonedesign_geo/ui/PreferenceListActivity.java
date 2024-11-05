package com.example.capstonedesign_geo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class PreferenceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_list);
        //StatusBarKt.setStatusBarColor(this, R.color.mainblue);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));


    }
}
