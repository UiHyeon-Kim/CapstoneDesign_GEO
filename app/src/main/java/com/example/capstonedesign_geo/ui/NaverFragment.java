package com.example.capstonedesign_geo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.capstonedesign_geo.R;
import com.gun0912.tedpermission.PermissionListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;


public class NaverFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private NaverMap naverMap;
    private MapView mapView; //지도 객체 변수

    public NaverFragment() { }

    public static NaverFragment newInstance(){
        NaverFragment fragment = new NaverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_naver, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE); //런타임 권한 처리를 위해 생성자에 액티비티 객체를 전달하고 권한 요청 코드를 지정해 줘야 한다

        return rootView;
    }



    @Override
    public void onMapReady(NaverMap naverMap){

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //배경 지도 선택
        naverMap.setMapType(NaverMap.MapType.Satellite);

        //건물 표시
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);

        //위치 및 각도 조정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(33.38, 126.55),   // 위치 지정
                9,                                     // 줌 레벨
                45,                                       // 기울임 각도
                45                                     // 방향
        );
        naverMap.setCameraPosition(cameraPosition);
    }

    @Override
    public void onStart()
    {
        String addr;

        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}