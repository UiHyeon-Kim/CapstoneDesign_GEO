package com.example.capstonedesign_geo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.data.db.GeoDatabase
import com.example.capstonedesign_geo.ui.adapter.PlaceAdapter
import com.example.capstonedesign_geo.ui.fragment.NaverMapData
import com.example.capstonedesign_geo.ui.fragment.NaverMapDataDao
import com.example.capstonedesign_geo.utility.setStatusBarColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlaceListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaceAdapter
    private lateinit var naverMapDataDao: NaverMapDataDao
    private val placeList = mutableListOf<NaverMapData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.capstonedesign_geo.R.layout.activity_place_list)
        this.setStatusBarColor(resources.getColor(com.example.capstonedesign_geo.R.color.mainblue))

        recyclerView = findViewById(com.example.capstonedesign_geo.R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PlaceAdapter(placeList) { place ->
            val intent = Intent(this, PlaceDetailActivity::class.java).apply {
                putExtra("title", place.title)
                putExtra("addr1", place.addr1)
                putExtra("tel", place.tel)
                putExtra("content", place.content)
                putExtra("firstimage", place.firstimage)
                putExtra("category", place.category)
                putExtra("amenity", place.amenity)
                putExtra("hours", place.hours)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val db = GeoDatabase.getInstance(this)
        naverMapDataDao = db.naverMapDataDao()

        // Intent에서 전달된 카테고리 또는 검색 결과 가져오기
        val category = intent.getStringExtra("category")
        val searchResults: ArrayList<NaverMapData>? =
            intent.getParcelableArrayListExtra("searchResults")

        if (category != null) {
            // 카테고리로 장소 검색
            loadPlacesByCategory(category)
        } else if (searchResults != null && searchResults.isNotEmpty()) {
            // 검색 결과 표시
            placeList.addAll(searchResults)
            adapter.notifyDataSetChanged()
        } else {
            // 데이터가 없을 경우 안내 메시지
            Toast.makeText(this, "결과를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        val btnBack = findViewById<ImageButton>(com.example.capstonedesign_geo.R.id.backButton)
        btnBack.setOnClickListener { view: View? -> onBackPressed() }
    }

    // 특정 카테고리에 맞는 장소를 데이터베이스에서 가져오는 함수
    private fun loadPlacesByCategory(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val results = naverMapDataDao.searchByText(category)
            withContext(Dispatchers.Main) {
                if (results.isNotEmpty()) {
                    placeList.clear()
                    placeList.addAll(results)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@PlaceListActivity,
                        "해당 카테고리의 장소를 찾을 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}