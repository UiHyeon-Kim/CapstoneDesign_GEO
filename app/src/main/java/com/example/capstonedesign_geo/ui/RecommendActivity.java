package com.example.capstonedesign_geo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.example.capstonedesign_geo.ui.adapter.PlaceImageAdapter;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;
import com.example.capstonedesign_geo.ui.fragment.NaverMapDataDao;

import java.util.List;

import kotlin.Unit;

public class RecommendActivity extends AppCompatActivity {

    private RecyclerView imageRecycler;
    private PlaceImageAdapter placeImageAdapter;
    private NaverMapDataDao naverMapDataDao;

    private ImageView placeImage1, placeImage2, placeImage3, placeImage4, placeImage5, placeImage6, placeImage7, placeImage8;
    private TextView placeText1, placeText2, placeText3, placeText4, placeText5, placeText6, placeText7, placeText8;

    private ImageView restorentImage, cafeImage, cultureImgae, shoppingImage, sportsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // RecyclerView 초기화
        imageRecycler = findViewById(R.id.imageRecycler);
        imageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        placeImage1 = findViewById(R.id.placeImage1);
        placeImage2 = findViewById(R.id.placeImage2);
        placeImage3 = findViewById(R.id.placeImage3);
        placeImage4 = findViewById(R.id.placeImage4);
        placeImage5 = findViewById(R.id.placeImage5);
        placeImage6 = findViewById(R.id.placeImage6);
        placeImage7 = findViewById(R.id.placeImage7);

        placeText1 = findViewById(R.id.placeText1);
        placeText2 = findViewById(R.id.placeText2);
        placeText3 = findViewById(R.id.placeText3);
        placeText4 = findViewById(R.id.placeText4);
        placeText5 = findViewById(R.id.placeText5);
        placeText6 = findViewById(R.id.placeText6);
        placeText7 = findViewById(R.id.placeText7);

        restorentImage = findViewById(R.id.restorentImage);
        cafeImage = findViewById(R.id.cafeImage);
        cultureImgae = findViewById(R.id.cultureImgae);
        shoppingImage = findViewById(R.id.shoppingImage);
        sportsImage = findViewById(R.id.sportsImage);

        restorentImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceListActivity.class);
            intent.putExtra("category", "식당");
            startActivity(intent);
        });
        cafeImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceListActivity.class);
            intent.putExtra("category", "카페");
            startActivity(intent);
        });
        cultureImgae.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceListActivity.class);
            intent.putExtra("category", "문화");
            startActivity(intent);
        });
        shoppingImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceListActivity.class);
            intent.putExtra("category", "쇼핑");
            startActivity(intent);
        });
        sportsImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaceListActivity.class);
            intent.putExtra("category", "스포츠");
            startActivity(intent);
        });


        // DB 초기화
        GeoDatabase db = GeoDatabase.getInstance(this);
        naverMapDataDao = db.naverMapDataDao();

        // 랜덤 장소 데이터 로드
        loadRandomPlaces();
        loadPopularPlaces();
    }

    private void loadPopularPlaces() {
        new Thread(() -> {
            List<NaverMapData> popularPlaces = naverMapDataDao.getPopularPlaces();

            runOnUiThread(() -> {
                if (popularPlaces != null && !popularPlaces.isEmpty()) {
                    placeImageAdapter = new PlaceImageAdapter(popularPlaces, place -> {
                        openPlaceDetail(place);
                        return Unit.INSTANCE;
                    });
                    imageRecycler.setAdapter(placeImageAdapter);
                }
            });
        }).start();
    }

    private void loadRandomPlaces() {
        new Thread(() -> {
            List<NaverMapData> randomPlaces = naverMapDataDao.getRandomPlaces();

            runOnUiThread(() -> {
                if (randomPlaces != null && !randomPlaces.isEmpty()) {

                    // 다른 이미지 뷰에 데이터 바인딩
                    if (randomPlaces.size() > 0) {
                        bindPlaceToView(randomPlaces.get(0), placeImage1, placeText1);
                    }
                    if (randomPlaces.size() > 1) {
                        bindPlaceToView(randomPlaces.get(1), placeImage2, placeText2);
                    }
                    if (randomPlaces.size() > 2) {
                        bindPlaceToView(randomPlaces.get(2), placeImage3, placeText3);
                    }
                    if (randomPlaces.size() > 3) {
                        bindPlaceToView(randomPlaces.get(3), placeImage4, placeText4);
                    }
                    if (randomPlaces.size() > 4) {
                        bindPlaceToView(randomPlaces.get(4), placeImage5, placeText5);
                    }
                    if (randomPlaces.size() > 5) {
                        bindPlaceToView(randomPlaces.get(5), placeImage6, placeText6);
                    }
                    if (randomPlaces.size() > 6) {
                        bindPlaceToView(randomPlaces.get(6), placeImage7, placeText7);
                    }
                }
            });
        }).start();
    }

    private void bindPlaceToView(NaverMapData place, ImageView imageView, TextView textView) {
        // 이미지 및 텍스트를 뷰에 바인딩
        Glide.with(this)
                .load(place.getFirstimage())
                .into(imageView);
        textView.setText(place.getTitle());

        // 클릭 시 상세 화면으로 이동
        imageView.setOnClickListener(v -> openPlaceDetail(place));
    }

    private void openPlaceDetail(NaverMapData place) {
        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra("title", place.getTitle());
        intent.putExtra("addr1", place.getAddr1());
        intent.putExtra("tel", place.getTel());
        intent.putExtra("content", place.getContent());
        intent.putExtra("firstimage", place.getFirstimage());
        intent.putExtra("category", place.getCategory());
        intent.putExtra("hours", place.getHours());
        intent.putExtra("amenity", place.getAmenity());
        startActivity(intent);
    }
}
