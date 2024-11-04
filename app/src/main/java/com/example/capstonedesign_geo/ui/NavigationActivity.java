package com.example.capstonedesign_geo.ui;

import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.NaverFragment;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.Locale;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback{

    private NaverMap naverMap;
    private EditText startLocation, endLocation;
    private Marker startMarker, endMarker;
    private boolean isSettingStartLocation = true; // 시작 위치 설정 여부
    private Geocoder geocoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //EditText 초기화
        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.endLocation);

        //Geocoder 초기화
        geocoder = new Geocoder(this, Locale.getDefault());

        // 지도 프래그먼트 설정
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.mapFragment, mapFragment).commit();
        }
        mapFragment.getMapAsync((OnMapReadyCallback) this); // NaverMap 준비
    }

    @Override
    public void onMapReady(NaverMap map) {
        this.naverMap = map;

        // 지도 클릭 이벤트 설정
        naverMap.setOnMapClickListener((point, latLng) -> {
            if (isSettingStartLocation) {
                // 출발지 마커 설정
                if (startMarker != null) startMarker.setMap(null); // 기존 마커 제거
                startMarker = new Marker();
                startMarker.setPosition(latLng);
                startMarker.setMap(naverMap);
                startLocation.setText("위도: " + latLng.latitude + ", 경도: " + latLng.longitude);
            } else {
                // 도착지 마커 설정
                if (endMarker != null) endMarker.setMap(null); // 기존 마커 제거
                endMarker = new Marker();
                endMarker.setPosition(latLng);
                endMarker.setMap(naverMap);
                endLocation.setText("위도: " + latLng.latitude + ", 경도: " + latLng.longitude);
            }

            // 카메라 이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
            naverMap.moveCamera(cameraUpdate);
        });


        // 기본 위치 설정 (예: 서울)
        LatLng defaultLocation = new LatLng(37.5665, 126.9780);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(defaultLocation);
        naverMap.moveCamera(cameraUpdate);
    }
}
