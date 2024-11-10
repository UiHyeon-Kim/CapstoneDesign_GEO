package com.example.capstonedesign_geo.ui;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.example.capstonedesign_geo.ui.fragment.MapInfoActivity;
import com.example.capstonedesign_geo.ui.fragment.NaverMapApiInterface;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;
import com.example.capstonedesign_geo.ui.fragment.NaverMapDataDao;
import com.example.capstonedesign_geo.ui.fragment.NaverMapItem;
import com.example.capstonedesign_geo.ui.fragment.NaverMapRequest;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.overlay.PolylineOverlay;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final List<Marker> nmarker = new ArrayList<>(); // 모든 마커를 저장할 리스트
    private boolean isSettingStartLocation = true; // 시작 위치 설정 여부
    private final boolean markersVisible = true;
    private NaverMapItem naverMapList;
    private Marker startMarker, endMarker;
    private NaverMap naverMap;
    private Geocoder geocoder;
    private List<NaverMapData> naverMapInfo;

    private AutoCompleteTextView startLocation, endLocation;
    private ArrayAdapter<String> adapter;
    private List<String> locationList = new ArrayList<>();
    private NaverMapDataDao naverMapDataDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.endLocation);
        setupAutoComplete();

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> findRoute());

        //Geocoder 초기화
        geocoder = new Geocoder(this, Locale.getDefault());

        // 지도 프래그먼트 설정
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.mapFragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this); // NaverMap 준비
    }

    @Override
    public void onMapReady(NaverMap map) {
        this.naverMap = map;

        // 지도 클릭 이벤트 설정
        naverMap.setOnMapClickListener((point, latLng) -> {
            String address = getAddressFromLatLng(latLng);

            if (isSettingStartLocation) {
                // 출발지 마커 설정
                if (startMarker != null) startMarker.setMap(null); // 기존 마커 제거
                startMarker = new Marker();
                startMarker.setPosition(latLng);
                startMarker.setMap(naverMap);
                startLocation.setText(address != null ? address : "위도: " + latLng.latitude + ", 경도: " + latLng.longitude);
            } else {
                // 도착지 마커 설정
                if (endMarker != null) endMarker.setMap(null); // 기존 마커 제거
                endMarker = new Marker();
                endMarker.setPosition(latLng);
                endMarker.setMap(naverMap);
                endLocation.setText(address != null ? address : "위도: " + latLng.latitude + ", 경도: " + latLng.longitude);
            }

            // 카메라 이동
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
            naverMap.moveCamera(cameraUpdate);

            // 출발지와 도착지를 번갈아 가며 설정하도록 변수 토글
            isSettingStartLocation = !isSettingStartLocation;
        });


        // 기본 위치 설정 (예: 서울)
        LatLng defaultLocation = new LatLng(37.5665, 126.9780);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(defaultLocation);
        naverMap.moveCamera(cameraUpdate);
    }


    private void setupAutoComplete() {
        naverMapDataDao = GeoDatabase.getInstance(this).naverMapDataDao();
        new Thread(() -> {
            List<NaverMapData> places = naverMapDataDao.getAll();
            for (NaverMapData place : places) {
                locationList.add(place.getTitle());
            }
            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locationList);
                startLocation.setAdapter(adapter);
                endLocation.setAdapter(adapter);
            });
        }).start();
    }

    // 1. 경로를 찾는 메소드
    private void findRoute() {
        String start = startLocation.getText().toString();
        String end = endLocation.getText().toString();

        if (start.isEmpty() || end.isEmpty()) {
            Toast.makeText(this, "출발지와 도착지를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String option = "trafast"; // 'trafast', 'tracomfort', 'traoptimal' 등의 옵션 사용 가능

        Retrofit retrofit =  NaverMapRequest.getNaverMapClient();
        NaverMapApiInterface apiService = retrofit.create(NaverMapApiInterface.class);

        Call<RouteResponse> call = apiService.getRoute(
                start,
                end,
                option,
                "jbhzsc7nk4",
                "2LbdnqflVOtaMupOcZdgDX1Tz2Z4jQLc3OLDfcAX"
        );

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayRouteOnMap(response.body());
                } else {
                    Log.e("API_ERROR_ROUTE", "Response Code: " + response.code());
                    Toast.makeText(NavigationActivity.this, "경로를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                Log.e("API_CALL_FAIL", "API 호출 실패: " + t.getMessage());
                Toast.makeText(NavigationActivity.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 2. 경로를 지도에 표시하는 메소드
    private void displayRouteOnMap(RouteResponse routeResponse) {
        if (naverMap != null && routeResponse.getRoute() != null) {
            List<LatLng> pathPoints = new ArrayList<>();
            for (RouteResponse.RoutePoint point : routeResponse.getRoute().getPoints()) {
                pathPoints.add(new LatLng(point.getLatitude(), point.getLongitude()));
            }

            PolylineOverlay polyline = new PolylineOverlay();
            polyline.setCoords(pathPoints);
            polyline.setColor(Color.BLUE);
            polyline.setMap(naverMap);

            if (!pathPoints.isEmpty()) {
                naverMap.moveCamera(CameraUpdate.scrollTo(pathPoints.get(0)));
            }
        }
    }


    private String getAddressFromLatLng(LatLng latLng) {
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            Log.e("Geocoder Error", "Failed to get address", e);
        }
        return null;
    }

}