package com.example.capstonedesign_geo.ui.fragment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NaverMapApiInterface {
    @GET("db.php")
    Call<NaverMapItem> getMapData();
}