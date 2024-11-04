package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.utility.StatusBarKt;

public class PlaceDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detailed_info);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String address = intent.getStringExtra("address");
        String hours = intent.getStringExtra("hours");
        String imageUrl = intent.getStringExtra("imageUrl");
        String tel = intent.getStringExtra("tel");
        String amenity = intent.getStringExtra("amenity");
        String shortAddr = intent.getStringExtra("shortAddr");

        TextView titleTextView = findViewById(R.id.title);
        TextView categoryTextView = findViewById(R.id.category);
        TextView addressTextView = findViewById(R.id.address);
        TextView hoursTextView = findViewById(R.id.hours);
        ImageView imageView = findViewById(R.id.header_image);
        TextView telTextView = findViewById(R.id.tel);
        TextView amenityTextView = findViewById(R.id.amenity);
        TextView shortAddrTextView = findViewById(R.id.shortAddress);
        /*Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("상단 바 제목");*/

        titleTextView.setText(title);
        categoryTextView.setText(category);
        addressTextView.setText(address);
        hoursTextView.setText(hours);
        telTextView.setText(tel);
        amenityTextView.setText(amenity);
        shortAddrTextView.setText(shortAddr);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

    }
}
