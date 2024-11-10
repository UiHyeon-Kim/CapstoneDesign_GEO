package com.example.capstonedesign_geo.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.PlaceDetailActivity;

public class MapInfoActivity extends AppCompatActivity {
    private ImageView getMapInfofirstimage;
    private TextView getMapInfoTitle;
    private TextView getMapInfoAddr1;
    private TextView getMapInfoAddr2;
    private TextView getMapInfohours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        setFinishOnTouchOutside(true); // 외부 터치 시 닫히도록 설정

        getMapInfofirstimage = findViewById(R.id.map_info_firstimage);
        getMapInfoTitle = findViewById(R.id.map_info_title);
        getMapInfoAddr1 = findViewById(R.id.map_info_addr1);
        getMapInfoAddr2 = findViewById(R.id.map_info_addr2);
        getMapInfohours = findViewById(R.id.map_info_time);

        Intent intent = getIntent();
        String firstimage = intent.getStringExtra("firstimage");
        String title = intent.getStringExtra("title");
        String addr1 = intent.getStringExtra("addr1");
        String addr2 = intent.getStringExtra("addr2");
        String hours = intent.getStringExtra("hours");


        getMapInfoTitle.setText(title);
        getMapInfoAddr1.setText(addr1);
        getMapInfoAddr2.setText(addr2);
        getMapInfohours.setText(hours);
        Glide.with(this)
                .load(firstimage) // 이미지 URL
                .into(getMapInfofirstimage); // ImageView에 설정

        initLayout(); // 화면 크기를 받아 레이아웃 설정(기종마다 크기 조정을 위해)

    }

    //정보창XML 화면 맞춰 띄우기
    private void initLayout() {
        // DisplayMetrics를 이용해 화면 크기를 가져옴
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // 레이아웃 크기와 위치 설정
        getWindow().setLayout((int) Math.round(width * 0.9), (int) Math.round(height * 0.22));
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void onLinearLayoutClick(View view) {
        // 클릭 시 실행할 코드(상세정보 창으로 선택한 장소 정보 보내기)
        Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);
        intent.putExtra("firstimage", getIntent().getStringExtra("firstimage"));
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("category", getIntent().getStringExtra("category"));
        intent.putExtra("addr1", getIntent().getStringExtra("addr1"));
        intent.putExtra("addr2", getIntent().getStringExtra("addr2"));
        intent.putExtra("hours", getIntent().getStringExtra("hours"));
        intent.putExtra("category", getIntent().getStringExtra("category"));
        intent.putExtra("tel", getIntent().getStringExtra("tel"));
        intent.putExtra("content", getIntent().getStringExtra("content"));
        intent.putExtra("amenity", getIntent().getStringExtra("amenity"));
        startActivity(intent);
    }
}
