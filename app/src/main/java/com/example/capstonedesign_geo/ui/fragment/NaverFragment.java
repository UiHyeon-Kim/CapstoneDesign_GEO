package com.example.capstonedesign_geo.ui.fragment;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.db.GeoDatabase;
import com.google.android.material.chip.Chip;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NaverFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000; //위치 권한 요청 코드
    private final List<Marker> nmarker = new ArrayList<>(); // 모든 마커를 저장할 리스트
    private final Map<String, Boolean> categoryVisibilityMap = new HashMap<>(); // 카테고리별 가시성 상태
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private NaverMap naverMap;
    private MapView mapView; //지도 객체 변수
    private NaverMapItem naverMapList;
    private List<NaverMapData> naverMapInfo;
    private boolean markersVisible = false; // 마커 초기 가시성 상태: false로 설정

    public NaverFragment() {
    }

    public static NaverFragment newInstance() { //프래그먼트 생성
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
        mapView = rootView.findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        //런타임 권한 처리를 위해 생성자에 액티비티 객체를 전달하고 권한 요청 코드를 지정해 줘야 한다
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE); // 위치 권한 요청

        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Chip chipCategory = view.findViewById(R.id.chip_all);
        Chip Category_restaurant = view.findViewById(R.id.chip_restaurant);
        Chip Category_cafe = view.findViewById(R.id.chip_cafe);
        Chip Category_park = view.findViewById(R.id.chip_park);
        Chip Category_shopping = view.findViewById(R.id.chip_shopping);
        Chip Category_lodging = view.findViewById(R.id.chip_lodging);
        Chip Category_camping = view.findViewById(R.id.chip_camping);
        Chip Category_sports = view.findViewById(R.id.chip_sports);
        Chip Category_Ruins = view.findViewById(R.id.chip_Ruins);
        Chip Category_travel = view.findViewById(R.id.chip_travel);
        Chip Category_temple = view.findViewById(R.id.chip_temple);
        Chip Category_Library = view.findViewById(R.id.chip_Library);
        Chip Category_shinhan_univ = view.findViewById(R.id.chip_shinhan_univ);

        // 클릭 이벤트 설정
        chipCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 수행할 동작
                toggleMarkers(); // Chip 클릭 시 마커 가시성 토글
            }
        });

        // all이외의 카테고리 클릭이벤트
        categoryVisibilityMap.put("category_restaurant", false);
        Category_restaurant.setOnClickListener(v -> toggleMarkersByCategory("식당"));

        categoryVisibilityMap.put("category_cafe", false);
        Category_cafe.setOnClickListener(v -> toggleMarkersByCategory("카페"));

        categoryVisibilityMap.put("category_park", false);
        Category_park.setOnClickListener(v -> toggleMarkersByCategory("공원"));

        categoryVisibilityMap.put("category_shopping", false);
        Category_shopping.setOnClickListener(v -> toggleMarkersByCategory("쇼핑"));

        categoryVisibilityMap.put("category_lodging", false);
        Category_lodging.setOnClickListener(v -> toggleMarkersByCategory("숙박"));

        categoryVisibilityMap.put("category_camping", false);
        Category_camping.setOnClickListener(v -> toggleMarkersByCategory("캠핑"));

        categoryVisibilityMap.put("category_sports", false);
        Category_sports.setOnClickListener(v -> toggleMarkersByCategory("스포츠"));

        categoryVisibilityMap.put("category_Ruins", false);
        Category_Ruins.setOnClickListener(v -> toggleMarkersByCategory("유적"));

        categoryVisibilityMap.put("category_travel", false);
        Category_travel.setOnClickListener(v -> toggleMarkersByCategory("체험여행"));

        categoryVisibilityMap.put("category_temple", false);
        Category_temple.setOnClickListener(v -> toggleMarkersByCategory("사찰"));

        categoryVisibilityMap.put("category_Library", false);
        Category_Library.setOnClickListener(v -> toggleMarkersByCategory("도서관"));

        categoryVisibilityMap.put("category_shinhan_univ", false);
        Category_shinhan_univ.setOnClickListener(v -> toggleMarkersByCategory("신한대"));
    }

    @Override
    public void onMapReady(NaverMap naverMap) { // 지도가 준비되면 호출

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); // 위치를 반환하는 구현체를 지정
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); // 현재 위치를 따라다님

        //배경 지도 선택
        naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);

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


        // 초기 상태에서는 마커를 추가하되, 표시하지 않음
        initializeMarkers();
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
                    markers[i].setMap(null);
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
                            Intent intent = new Intent(requireActivity(), MapInfoActivity.class);
                            String mapInfofirstimage = naverMapInfo.get(index).getFirstimage();
                            String mapInfoTitle = naverMapInfo.get(index).getTitle();
                            String mapInfoAddr1 = naverMapInfo.get(index).getAddr1();
                            String mapInfoAddr2 = naverMapInfo.get(index).getAddr2();
                            String mapInfoTime = naverMapInfo.get(index).getHours();
                            intent.putExtra("title", mapInfoTitle);
                            intent.putExtra("addr1", mapInfoAddr1);
                            intent.putExtra("tel", naverMapInfo.get(index).getTel());
                            intent.putExtra("content", naverMapInfo.get(index).getContent());
                            intent.putExtra("firstimage", mapInfofirstimage);
                            intent.putExtra("category", naverMapInfo.get(index).getCategory());
                            intent.putExtra("amenity", naverMapInfo.get(index).getAmenity());
                            intent.putExtra("hours", naverMapInfo.get(index).getHours());
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

    // 마커 가시성을 전환하는 메서드
    private void toggleMarkers() {
        markersVisible = !markersVisible;

        for (Marker marker : nmarker) {
            marker.setMap(markersVisible ? naverMap : null);// markersVisible이 true일 때만 지도에 표시
        }
    }

    //카테고리별 마커 메서드
    private void toggleMarkersByCategory(String category) {
        // 카테고리 가시성 반전
        boolean isVisible = !categoryVisibilityMap.getOrDefault(category, false);
        categoryVisibilityMap.put(category, isVisible);

        // 마커를 가시성 상태에 따라 표시/숨김
        for (Marker marker : nmarker) {
            if (category.equals(marker.getTag())) {
                marker.setMap(isVisible ? naverMap : null);
            }
        }
    }

    @Override
    public void onStart() {
        String addr;

        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
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
                // 데이터가 존재하지 않을 때만 삽입
                if (dao.getCountByTitle(item.getTitle()) == 0) {
                    dao.insert(item);
                }
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