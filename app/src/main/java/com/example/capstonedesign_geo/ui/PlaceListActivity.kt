package com.example.capstonedesign_geo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.ui.adapter.PlaceAdapter
import com.example.capstonedesign_geo.ui.fragment.NaverMapData
import com.example.capstonedesign_geo.utility.setStatusBarColor

class PlaceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaceAdapter
    private val placeList = mutableListOf<NaverMapData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)
        this.setStatusBarColor(resources.getColor(R.color.mainblue))

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PlaceAdapter(placeList) { place ->
            val intent = Intent(this, PlaceDetailActivity::class.java).apply {
                putExtra("title", place.title)
                putExtra("category", place.category)
                putExtra("shortAddr", place.addr1)
                putExtra("address", "${place.addr1} ${place.addr2}")
                putExtra("hours", place.hours)
                putExtra("tel", place.tel)
                putExtra("amenity", place.amenity)
                putExtra("imageUrl", place.firstimage)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        // Intent로 전달된 데이터를 받음
        val searchResults: ArrayList<NaverMapData>? =
            intent.getParcelableArrayListExtra("searchResults")

        // RecyclerView 어댑터 설정
        if (searchResults != null && searchResults.isNotEmpty()) {
            placeList.addAll(searchResults)
            adapter.notifyDataSetChanged()
        } else {
            // 검색 결과가 없을 경우 처리 (예: 안내 메시지 표시)
        }
    }
}