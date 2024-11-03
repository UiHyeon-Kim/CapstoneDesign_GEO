package com.example.capstonedesign_geo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.ui.adapter.PlaceAdapter
import com.example.capstonedesign_geo.ui.fragment.NaverMapData

class PlaceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Intent로 전달된 데이터를 받음
        val searchResults: ArrayList<NaverMapData>? =
            intent.getParcelableArrayListExtra("searchResults")

        // RecyclerView 어댑터 설정
        if (searchResults != null && searchResults.isNotEmpty()) {
            val adapter = PlaceAdapter(searchResults)
            recyclerView.adapter = adapter
        } else {
            // 검색 결과가 없을 경우 처리 (예: 안내 메시지 표시)
        }
    }
}