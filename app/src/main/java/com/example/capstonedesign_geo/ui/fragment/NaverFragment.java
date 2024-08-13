package com.example.capstonedesign_geo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.capstonedesign_geo.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;


public class NaverFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000; //위치 권한 요청 코드
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private NaverMap naverMap;
    private MapView mapView; //지도 객체 변수

    public NaverFragment() { }

    public static NaverFragment newInstance(){ //프래그먼트 생성
        NaverFragment fragment = new NaverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { //프래그먼트가 화면에 표시될 때 호출
        // Inflate the layout for this fragment
        /* Inflate : xml에 표기된 레이아웃들을 메모리에 로딩된 후 객체화 시키는 과정 (Activity에서 setContentView()가 xml을 Inflate하는 동작)
        *  쉽게 말해 layout에 그때그때 다른 layout을 집어넣을 수 있음.각기 다른 화면들을 한 화면에 동적으로 띄우고 싶은 경우 사용 */
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_naver, container, false); // 프래그먼트 레이아웃을 inflate
        mapView = (MapView) rootView.findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        //런타임 권한 처리를 위해 생성자에 액티비티 객체를 전달하고 권한 요청 코드를 지정해 줘야 한다
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE); // 위치 권한 요청

        return rootView;
    }

    @Override
    public void onMapReady(NaverMap naverMap){ // 지도가 준비되면 호출

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); // 위치를 반환하는 구현체를 지정
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); // 현재 위치를 따라다님

        //배경 지도 선택
        naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);

        //실내지도표시
        naverMap.setIndoorEnabled(true);

        // 시점 위치 및 각도 조정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.709, 127.04), // 위치 지정
                16, // 줌 레벨
                0,  // 기울임 각도
                0   // 방향
        );
        naverMap.setCameraPosition(cameraPosition);

        UiSettings uiSettings = naverMap.getUiSettings(); //UI컨트롤 활성화 객체
        uiSettings.setCompassEnabled(true);         //나침판
        uiSettings.setScaleBarEnabled(true);        //축적바(그 얼마나 확대 했나~그거)
        uiSettings.setZoomControlEnabled(true);     //확대 축소 버튼
        uiSettings.setLocationButtonEnabled(true);  //현위치 버튼

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