package com.example.capstonedesign_geo.ui;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.MapInfoActivity;
import com.example.capstonedesign_geo.ui.fragment.NaverMapApiInterface;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;
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
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final List<Marker> nmarker = new ArrayList<>(); // 모든 마커를 저장할 리스트
    private final boolean isSettingStartLocation = true; // 시작 위치 설정 여부
    private final boolean markersVisible = true;
    private EditText startLocation, endLocation;
    private NaverMapItem naverMapList;
    private Marker startMarker, endMarker;
    private NaverMap naverMap;
    private Geocoder geocoder;
    private List<NaverMapData> naverMapInfo;

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
        mapFragment.getMapAsync(this); // NaverMap 준비
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

    // 마커를 지도에 추가하는 메서드
    private void initializeMarkers() {
        //클라이언트 객체 생성
        NaverMapApiInterface naverMapApiInterface = NaverMapRequest.getClient().create(NaverMapApiInterface.class);
        // 응답을 받을 콜백 구현
        Call<NaverMapItem> call = naverMapApiInterface.getMapData();
        //클라이언트 객체가 제공하는 enqueue로 통신에 대한 요청, 응답 처리 방법 명시
        call.enqueue(new Callback<NaverMapItem>() {
            @Override
            public void onResponse(Call<NaverMapItem> call, Response<NaverMapItem> response) {

                naverMapList = response.body();
                naverMapInfo = naverMapList.result;

                // 마커 여러개 찍기
                for (int i = 0; i < naverMapInfo.size(); i++) {
                    final int index = i;
                    Marker[] markers = new Marker[naverMapInfo.size()];

                    markers[i] = new Marker();
                    double lnt = naverMapInfo.get(i).getMapx();
                    double lat = naverMapInfo.get(i).getMapy();
                    markers[i].setPosition(new LatLng(lat, lnt));
                    markers[i].setMap(naverMap);
                    markers[i].setCaptionText(naverMapInfo.get(i).getTitle());
                    markers[i].setIcon(MarkerIcons.BLACK);
                    markers[i].setIconTintColor(ContextCompat.getColor(context, R.color.mainblue));

                    // 초기에는 지도에 표시하지 않음
//                    markers[i].setMap(null);
                    nmarker.add(markers[i]); // 리스트에 추가

                    // 각 마커에 카테고리를 태그로 설정
                    markers[i].setTag(naverMapInfo.get(i).getCategory());

                    markers[i].setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            // 마커의 위치를 가져옴
                            LatLng markerPosition = markers[index].getPosition();
                            // CameraUpdate 객체를 사용해 마커 위치로 이동
                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(markerPosition)
                                    .animate(CameraAnimation.Easing); // 애니메이션 추가 (선택 사항)
                            // NaverMap 객체를 사용해 카메라 위치 업데이트
                            naverMap.moveCamera(cameraUpdate);

                            //클릭시 밑에 장소 정보가 뜨게 하기
                            Intent intent = new Intent(NavigationActivity.this, MapInfoActivity.class);
                            String mapInfofirstimage = naverMapInfo.get(index).getFirstimage();
                            String mapInfoTitle = naverMapInfo.get(index).getTitle();
                            String mapInfoAddr1 = naverMapInfo.get(index).getAddr1();
                            String mapInfoAddr2 = naverMapInfo.get(index).getAddr2();
                            String mapInfoTime = naverMapInfo.get(index).getHours();
                            intent.putExtra("firstimage", mapInfofirstimage);
                            intent.putExtra("title", mapInfoTitle);
                            intent.putExtra("addr1", mapInfoAddr1);
                            intent.putExtra("addr2", mapInfoAddr2);
                            intent.putExtra("time", mapInfoTime);
                            startActivity(intent);
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<NaverMapItem> call, Throwable t) {
                Log.e("API_ERROR", "통신 실패", t);
                //Toast.makeText(getContext(), "실패: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}