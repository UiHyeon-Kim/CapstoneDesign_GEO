package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
        String hours = intent.getStringExtra("hours");
        String imageUrl = intent.getStringExtra("firstimage");
        String tel = intent.getStringExtra("tel");
        String amenity = intent.getStringExtra("amenity");
        String addr1 = intent.getStringExtra("addr1");
        String content = intent.getStringExtra("content");

        TextView titleTextView = findViewById(R.id.title);
        TextView categoryTextView = findViewById(R.id.category);
        TextView addressTextView = findViewById(R.id.address);
        TextView hoursTextView = findViewById(R.id.hours);
        ImageView imageView = findViewById(R.id.header_image);
        TextView telTextView = findViewById(R.id.tel);
        TextView amenityTextView = findViewById(R.id.amenity);
        TextView shortAddrTextView = findViewById(R.id.shortAddress);
        TextView descriptionTextView = findViewById(R.id.description);

        titleTextView.setText(title);
        categoryTextView.setText(category);
        addressTextView.setText(addr1);
        hoursTextView.setText(hours);
        telTextView.setText(tel);
        amenityTextView.setText(amenity);
        shortAddrTextView.setText(addr1);
        descriptionTextView.setText(content);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

        AppCompatButton btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            String shareTitle = title;
            String shareAddress = addr1;
            String shareTel = tel;

            String shareText = "장소 이름: " + shareTitle + "\n주소: " + shareAddress + "\n전화번호: " + shareTel;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "장소 공유");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "공유하기"));
        });

        AppCompatButton btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(v -> {
            String phoneNumber = tel;
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        });

        AppCompatButton btnNavigate = findViewById(R.id.btnNavigate);
        btnNavigate.setOnClickListener(v -> {
            Intent naviIntent = new Intent(PlaceDetailActivity.this, NavigationActivity.class);
            naviIntent.putExtra("place_name", "장소 이름");
            startActivity(naviIntent);
        });

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> onBackPressed());
    }

}
