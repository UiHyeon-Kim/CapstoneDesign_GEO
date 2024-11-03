package com.example.capstonedesign_geo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;

public class MapInfoDetail extends AppCompatActivity {

    private ImageView getMapInfofirstimage;
    private TextView getMapInfoTitle;
    private TextView getMapInfoAddr1;
    private TextView getMapInfoAddr2;
    private TextView getMapInfoTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detailed_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getMapInfofirstimage = findViewById(R.id.header_image);
        getMapInfoTitle = findViewById(R.id.map_detail_title);
        getMapInfoAddr1 = findViewById(R.id.map_detail_addr1);
        getMapInfoAddr2 = findViewById(R.id.map_detail_addr2);
        getMapInfoTime = findViewById(R.id.map_detail_time);

        Intent intent = getIntent();
        String firstimage = intent.getStringExtra("firstimage");
        String title = intent.getStringExtra("title");
        String addr1 = intent.getStringExtra("addr1");
        String addr2 = intent.getStringExtra("addr2");
        String time = intent.getStringExtra("time");

        getMapInfoTitle.setText(title);
        getMapInfoAddr1.setText(addr1);
        getMapInfoAddr2.setText(addr2);
        getMapInfoTime.setText(time);
        Glide.with(this)
                .load(firstimage) // 이미지 URL
                .into(getMapInfofirstimage); // ImageView에 설정

        // 뒤로가기 버튼 활성화
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    // 뒤로가기 버튼 클릭 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // 뒤로가기 동작
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}