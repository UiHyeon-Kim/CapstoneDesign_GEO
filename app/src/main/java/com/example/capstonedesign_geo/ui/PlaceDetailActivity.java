package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;

public class PlaceDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detailed_info);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String address = intent.getStringExtra("address");
        String hours = intent.getStringExtra("hours");
        String imageUrl = intent.getStringExtra("imageUrl");

        TextView titleTextView = findViewById(R.id.title);
        TextView categoryTextView = findViewById(R.id.category);
        TextView addressTextView = findViewById(R.id.addr);
        //TextView hoursTextView = findViewById(R.id.hours);
        ImageView imageView = findViewById(R.id.header_image);

        titleTextView.setText(title);
        categoryTextView.setText(category);
        addressTextView.setText(address);
        //hoursTextView.setText(hours);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

    }
}
