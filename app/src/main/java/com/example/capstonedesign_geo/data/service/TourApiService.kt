package com.example.capstonedesign_geo.data.service

import retrofit2.http.GET
import retrofit2.http.Query

interface TourApiService {
    //@GET("getData")
    @GET("http://apis.data.go.kr/B551011/KorService1")
    fun getTourList(
        // TourAPI에서 가져올 목록 (TourAPI 기준)
        // @Query("항목명")변수명: 타입
        @Query("numOfRows")numOfRows: Int,      // 한 페이지 결과 수
        @Query("pageNo")pageNo: Int,            // 페이지 번호
        @Query("MobileOS")MobileOS: String,     // OS 구분
        @Query("MobileApp")MobileApp: String,   // 서비스 명
        @Query("serviceKey")serviceKey: String, // 인증키
        @Query("_type")_type: String,           // 응답 데이터 형식")
        @Query("areaCode")areaCode: String,     // 지역 코드
        @Query("contentTypeId")contentTypeId: String, // 관광 타입

    )

    fun getTourDetail (

    )
}