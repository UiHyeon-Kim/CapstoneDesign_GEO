package com.example.capstonedesign_geo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.ui.adapter.TourListAdapter
import com.example.capstonedesign_geo.viewmodel.TourViewModel

class PlaceListActivity : AppCompatActivity() {

    private lateinit var tourViewModel: TourViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tourViewModel = ViewModelProvider(this)[TourViewModel::class.java]

        // 어댑터 설정
        val adapter = TourListAdapter(emptyList()) { selectedItem ->
            val intent = Intent(this, PlaceDetailActivity::class.java)
            intent.putExtra("item", selectedItem)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // 데이터 관찰
        tourViewModel.tourItems.observe(this) { items ->
            adapter.updateItems(items)  // 데이터를 어댑터에 업데이트
        }

        tourViewModel.fetchTourInfo()
    }
}