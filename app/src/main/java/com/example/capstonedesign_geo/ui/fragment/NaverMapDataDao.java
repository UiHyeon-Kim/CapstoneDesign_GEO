package com.example.capstonedesign_geo.ui.fragment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NaverMapDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NaverMapData data);

    @Query("SELECT * FROM naver_map_data")
    List<NaverMapData> getAll();
}
