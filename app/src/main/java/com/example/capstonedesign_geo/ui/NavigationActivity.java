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
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
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

    private boolean isRequestInProgress = false;
    private AutoCompleteTextView startLocation, endLocation;
    private ArrayAdapter<String> adapter;
    private List<String> locationList = new ArrayList<>();
    private NaverMapDataDao naverMapDataDao;
    private PolylineOverlay polylineOverlay = null;
    Button searchButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.endLocation);
        setupAutoComplete();

        searchButton = findViewById(R.id.searchButton);
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
            if (isRequestInProgress) return; // 중복 요청 방지

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

//        String start = startLocation.getText().toString();
//        String end = endLocation.getText().toString();

//        if (start.isEmpty() || end.isEmpty()) {
//            Toast.makeText(this, "출발지와 도착지를 입력하세요.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (isRequestInProgress) {
            Toast.makeText(this, "경로를 요청 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String startLocationText = startLocation.getText().toString();
        String endLocationText = endLocation.getText().toString();

        if (startLocationText.isEmpty() || endLocationText.isEmpty()) {
            Toast.makeText(this, "출발지와 도착지를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng startCoords = getLatLngFromAddress(startLocationText);
        LatLng endCoords = getLatLngFromAddress(endLocationText);


        if (startCoords == null || endCoords == null) {
            Toast.makeText(this, "올바른 좌표를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String start = startCoords.longitude + "," + startCoords.latitude;
        String end = endCoords.longitude + "," + endCoords.latitude;
        String option = "trafast"; // 'trafast', 'tracomfort', 'traoptimal' 등의 옵션 사용 가능

        isRequestInProgress = true;
        searchButton.setEnabled(false); // 버튼 비활성화


        Log.d("API_DES_REQUEST", "Start: " + start + ", End: " + end);
        Log.d("API_REQUEST_COORDS", "Start Coordinates: " + start);
        Log.d("API_REQUEST_COORDS", "End Coordinates: " + end);
        Log.d("API_REQUEST_HEADERS", "Client ID: jbhzsc7nk4, Client Secret: 2LbdnqflVOtaMupOcZdgDX1Tz2Z4jQLc3OLDfcAX");


        Retrofit retrofit =  NaverMapRequest.getNaverMapClient();
        NaverMapApiInterface apiService = retrofit.create(NaverMapApiInterface.class);

        Call<RouteResponse> call = apiService.getRoute(
                "jbhzsc7nk4", // 네이버 API Client ID
                "2LbdnqflVOtaMupOcZdgDX1Tz2Z4jQLc3OLDfcAX", // 네이버 API Client Secret
                start,
                end,
                option
        );

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                isRequestInProgress = false;
                searchButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Route data: " + response.body().toString());
                    displayRouteOnMap(response.body());
                } else {
                    Toast.makeText(NavigationActivity.this, "경로를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR_ROUTE", "Response Code: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("API_ERROR_BODY", response.errorBody().string());
                        }
                    } catch (Exception e) {
                        Log.e("API_ERROR", "Error reading error body", e);
                    }
                    Toast.makeText(NavigationActivity.this, "경로를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                Log.e("API_CALL_FAIL", "API 호출 실패: " + t.getMessage());
                searchButton.setEnabled(true);
                isRequestInProgress = false;
                Toast.makeText(NavigationActivity.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 2. 경로를 지도에 표시하는 메소드
    private void displayRouteOnMap(RouteResponse routeResponse) {
        try {
            if (routeResponse == null || routeResponse.getRoute() == null) {
                Toast.makeText(this, "경로 데이터를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            List<RouteResponse.RouteItem> routeItems = routeResponse.getRoute().getTraoptimal();
            if (routeItems == null || routeItems.isEmpty()) return;

            RouteResponse.RouteItem routeItem = routeItems.get(0);
            List<RouteResponse.RoutePoint> path = routeItem.getPath();

            List<LatLng> latLngList = new ArrayList<>();
            for (RouteResponse.RoutePoint point : path) {
                latLngList.add(new LatLng(point.getLatitude(), point.getLongitude()));
            }

            if (polylineOverlay != null) polylineOverlay.setMap(null);

            polylineOverlay = new PolylineOverlay();
            polylineOverlay.setCoords(latLngList);
            polylineOverlay.setColor(Color.BLUE);
            polylineOverlay.setWidth(10);
            polylineOverlay.setMap(naverMap);

            adjustCameraToRoute(latLngList);

        } catch (Exception e) {
            Log.e("NAVIGATION_ERROR", "경로 표시 중 오류 발생", e);
        }
    }
//        if (routeResponse == null || routeResponse.getRoute() == null) {
//            Log.e("NAVIGATION_ERROR", "경로 데이터를 가져올 수 없습니다.");
//            Toast.makeText(this, "경로 데이터를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // 응답 데이터를 로그로 출력 (전체 RouteResponse 데이터)
//        Log.d("API_RESPONSE", "RouteResponse: " + new Gson().toJson(routeResponse));
//
//        List<RouteResponse.RouteItem> routeItems = routeResponse.getRoute().getTraoptimal();
//        if (routeItems == null || routeItems.isEmpty()) {
//            Log.e("NAVIGATION_ERROR", "유효한 경로 데이터를 찾을 수 없습니다.");
//            return;
//        }
//
//        RouteResponse.RouteItem routeItem = routeItems.get(0);
//        List<RouteResponse.RoutePoint> path = routeItem.getPath();
//        if (path == null || path.isEmpty()) {
//            Log.e("NAVIGATION_ERROR", "경로 점 데이터를 가져올 수 없습니다.");
//            return;
//        }
//
//        // 경로 점 목록의 크기를 로그로 출력
//        Log.d("MAP_COORDS", "Path Points Count: " + path.size());
//
//        // Polyline 그리기
//        List<LatLng> latLngList = new ArrayList<>();
//        for (RouteResponse.RoutePoint point : path) {
//            latLngList.add(new LatLng(point.getLatitude(), point.getLongitude()));
//            // 각 경로 점을 로그로 출력
//            Log.d("MAP_POINT", "Lat: " + point.getLatitude() + ", Lng: " + point.getLongitude());
//        }
//
//        if (polylineOverlay != null) {
//            polylineOverlay.setMap(null); // 기존 Polyline 제거
//        }
//
//        polylineOverlay = new PolylineOverlay();
//        polylineOverlay.setCoords(latLngList);
//        polylineOverlay.setColor(Color.BLUE);
//        polylineOverlay.setWidth(10);
//        polylineOverlay.setMap(naverMap);
//
//        adjustCameraToRoute(latLngList);
//
//        Log.d("NAVIGATION_SUCCESS", "경로가 지도에 성공적으로 표시되었습니다.");
//
//        // 지도의 중심을 경로의 중간 지점으로 이동
//        if (!latLngList.isEmpty()) {
//            naverMap.moveCamera(CameraUpdate.scrollTo(latLngList.get(latLngList.size() / 2)));
//        }





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

    private LatLng getLatLngFromAddress(String address) {
        try {
            List<android.location.Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {
            Log.e("Geocoder Error", "Failed to get coordinates from address", e);
        }
        return null;
    }

    private void adjustCameraToRoute(List<LatLng> latLngList) {
        if (latLngList.isEmpty()) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngList) {
            boundsBuilder.include(latLng);
        }

        LatLngBounds bounds = boundsBuilder.build();
        naverMap.moveCamera(CameraUpdate.fitBounds(bounds, 100));
    }


}