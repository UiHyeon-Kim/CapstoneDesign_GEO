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

    // 입력된 텍스트가 제목이나 주소에 포함된 데이터를 검색
    @Query("SELECT * FROM naver_map_data WHERE title LIKE :searchText OR addr1 LIKE :searchText")
    List<NaverMapData> searchByText(String searchText);
}
