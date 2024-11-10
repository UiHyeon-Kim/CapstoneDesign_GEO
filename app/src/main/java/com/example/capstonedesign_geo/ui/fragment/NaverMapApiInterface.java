package com.example.capstonedesign_geo.ui.fragment;

import com.example.capstonedesign_geo.ui.RouteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface NaverMapApiInterface {
    @GET("db.php")
    Call<NaverMapItem> getMapData();

    @GET("driving")
    Call<RouteResponse> getRoute(
            @Header("X-NCP-APIGW-API-KEY-ID") String clientId,
            @Header("X-NCP-APIGW-API-KEY") String clientSecret,
            @Query("start") String start,
            @Query("goal") String end,
            @Query("option") String option
    );
}
