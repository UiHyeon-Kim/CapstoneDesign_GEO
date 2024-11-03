package com.example.capstonedesign_geo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NaverFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000; //위치 권한 요청 코드
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private NaverMap naverMap;
    private MapView mapView; //지도 객체 변수

    private NaverMapItem naverMapList;
    private List<NaverMapData> naverMapInfo;

    public NaverFragment() { }

    public static NaverFragment newInstance(){ //프래그먼트 생성
        NaverFragment fragment = new NaverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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

        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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

        //클라이언트 객체 생성
        NaverMapApiInterface naverMapApiInterface = NaverMapRequest.getClient().create(NaverMapApiInterface.class);
        // 응답을 받을 콜백 구현
        Call<NaverMapItem> call = naverMapApiInterface.getMapData();
        //클라이언트 객체가 제공하는 enqueue로 통신에 대한 요청, 응답 처리 방법 명시
        call.enqueue(new Callback<NaverMapItem>() {
            @Override
            public void onResponse(Call<NaverMapItem> call, Response<NaverMapItem> response) {
                //원격 DB의 데이터를 로컬 DB에 삽입하는 코드
                if (response.isSuccessful() && response.body() != null) {
                    List<NaverMapData> dataList = response.body().result;
                    if (dataList != null && !dataList.isEmpty()) {
                        // 데이터를 사용하거나 Room DB에 삽입
                        insertDataIntoLocalDatabase(dataList);
                    }
                } else {
                    // 오류 처리
                    Log.e("LOCAL_DATABASE_ERROR", "로컬 DB 저장 실패");
                }

                naverMapList = response.body();
                naverMapInfo = naverMapList.result;

                // 마커 여러개 찍기
                for(int i=0; i < naverMapInfo.size(); i++){

                    final int index = i;

                    Marker[] markers = new Marker[naverMapInfo.size()];

                    markers[i] = new Marker();
                    double lnt = naverMapInfo.get(i).getMapx();
                    double lat = naverMapInfo.get(i).getMapy();
                    markers[i].setPosition(new LatLng(lat, lnt));
                    markers[i].setMap(naverMap);
                    markers[i].setCaptionText(naverMapInfo.get(i).getTitle());

                    markers[i].setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트
                        @Override
                        public boolean onClick(@NonNull Overlay overlay)
                        {//클릭시 밑에 장소 정보가 뜨게 하기
                            Intent intent = new Intent(requireActivity(), MapInfoActivity.class);

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
                Toast.makeText(getContext(), "실패: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

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

    private void insertDataIntoLocalDatabase(List<NaverMapData> dataList) {
        // Room 데이터베이스 인스턴스 가져오기
        GeoDatabase db = GeoDatabase.getInstance(getContext());

        // 백그라운드 스레드에서 데이터 삽입
        new Thread(() -> {
            NaverMapDataDao dao = db.naverMapDataDao();
            for (NaverMapData item : dataList) {
                dao.insert(item);
            }
        }).start();
    }
    //데이터를 가져오고 로그 출력
    private void checkDataInLocalDatabase() {
        GeoDatabase db = GeoDatabase.getInstance(getContext());

        // 백그라운드 스레드에서 데이터 가져오기
        new Thread(() -> {
            NaverMapDataDao dao = db.naverMapDataDao();
            List<NaverMapData> dataList = dao.getAll();

            // 데이터가 비어 있지 않은지 확인
            if (dataList != null && !dataList.isEmpty()) {
                // 데이터 로그 출력
                for (NaverMapData data : dataList) {
                    Log.d("LocalDB", "Title: " + data.getTitle() + ", Address: " + data.getAddr1());
                }
            } else {
                Log.d("LocalDB", "No data found in the local database.");
            }
        }).start();
    }


}