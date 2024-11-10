package com.example.capstonedesign_geo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;

public class MapInfoDetail extends AppCompatActivity {

    private ImageView getMapInfofirstimage;
    private TextView getMapInfoTitle;
    private TextView getMapInfoAddr1;
    private TextView getMapInfoAddr2;
    private TextView getMapInfoTime;
    private TextView getMapInfoCategory;
    private TextView getMapInfoTel;
    private TextView getMapInfoContent;
    private TextView getMapInfoAmenity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detailed_info);

        getMapInfofirstimage = findViewById(R.id.header_image);
        getMapInfoTitle = findViewById(R.id.title);
        getMapInfoAddr1 = findViewById(R.id.shortAddress);
        getMapInfoAddr2 = findViewById(R.id.address);
        getMapInfoTime = findViewById(R.id.hours);
        getMapInfoCategory = findViewById(R.id.category);
        getMapInfoTel = findViewById(R.id.tel);
        getMapInfoContent = findViewById(R.id.description);
        getMapInfoAmenity = findViewById(R.id.amenity);

        Intent intent = getIntent();
        String firstimage = intent.getStringExtra("firstimage");
        String title = intent.getStringExtra("title");
        String addr1 = intent.getStringExtra("addr1");
        String addr2 = intent.getStringExtra("addr2");
        String time = intent.getStringExtra("time");
        String category = intent.getStringExtra("category");
        String tel = intent.getStringExtra("tel");
        String content = intent.getStringExtra("content");
        String amenity = intent.getStringExtra("amenity");

        getMapInfoTitle.setText(title);
        getMapInfoAddr1.setText(addr1);
        getMapInfoAddr2.setText(addr2);
        getMapInfoTime.setText(time);
        getMapInfoCategory.setText(category);
        getMapInfoTel.setText(tel);
        getMapInfoContent.setText(content);
        getMapInfoAmenity.setText(amenity);
        Glide.with(this)
                .load(firstimage) // 이미지 URL
                .into(getMapInfofirstimage); // ImageView에 설정


    }




}