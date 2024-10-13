package com.example.capstonedesign_geo.data.network

import com.example.capstonedesign_geo.BuildConfig
import com.example.capstonedesign_geo.data.model.TourApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TourApiService {
    // API와 상호작용하기 위한 요청 설정
    // 장소 리스트 - TourAPI 지역기반관광정보
    @GET("areaBasedList1?serviceKey=${BuildConfig.TOURAPI}")
    suspend fun getTourList(
        // @Query("항목명")변수명: 타입
        @Query("numOfRows")numOfRows: Int,        // 한 페이지 결과 수
        @Query("pageNo")pageNo: Int,              // 페이지 번호
        @Query("MobileOS")MobileOS: String,       // OS 구분
        @Query("MobileApp")MobileApp: String,     // 서비스 명
        // @Query("serviceKey")serviceKey: String,   // 인증키
        @Query("listYN")listYN: String,           // 목록 조회 여부
        @Query("arrange")arrange: String,         // 정렬
        @Query("contentTypeId")contentTypeId: String, // 관광 타입
        @Query("areaCode")areaCode: String,       // 지역 코드
        @Query("sigunguCode")sigunguCode: String, // 시군구 코드
        @Query("cat1")cat1: String,               // 대분류
        @Query("cat2")cat2: String,               // 중분류
        @Query("cat3")cat3: String,               // 소분류
        @Query("_type")_type: String,             // 응답 데이터 형식
    ): TourApiResponse // 응답 데이터 클래스

    // 장소 상세 정보 - TourAPI 공통 정보 조회
    @GET("detailCommon1?serviceKey=${BuildConfig.TOURAPI}")
    suspend fun getTourDetail (

    )
}