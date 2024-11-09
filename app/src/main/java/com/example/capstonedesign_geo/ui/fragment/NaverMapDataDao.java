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

    // 랜덤으로 5개의 데이터를 가져오는 쿼리 추가
    @Query("SELECT * FROM naver_map_data ORDER BY RANDOM() LIMIT 10")
    List<NaverMapData> getRandomPlaces();

    @Query("SELECT * FROM naver_map_data WHERE category IN ('신한대', '카페', '공원') ORDER BY RANDOM() LIMIT 5")
    List<NaverMapData> getPopularPlaces();

    // 입력된 텍스트가 제목이나 주소에 포함된 데이터를 검색
    @Query("SELECT * FROM naver_map_data WHERE category LIKE :searchText OR title LIKE :searchText OR addr1 LIKE :searchText")
    List<NaverMapData> searchByText(String searchText);

    @Query("SELECT * FROM naver_map_data WHERE category LIKE :searchText OR title LIKE :searchText OR addr1 LIKE :searchText ORDER BY RANDOM() LIMIT 5")
    List<NaverMapData> getRandomPlaces(String searchText);

    // 특정 데이터가 존재하는지 확인하는 쿼리
    @Query("SELECT COUNT(*) FROM naver_map_data WHERE title = :title")
    int getCountByTitle(String title);
}
