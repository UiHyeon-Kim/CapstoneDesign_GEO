package com.example.capstonedesign_geo.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign_geo.utility.setStatusBarColor


class RecentSearchActivity : AppCompatActivity() {
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(com.example.capstonedesign_geo.R.layout.activity_recent_search)
        this.setStatusBarColor(resources.getColor(com.example.capstonedesign_geo.R.color.mainblue))

        val btnBack = findViewById<ImageButton>(com.example.capstonedesign_geo.R.id.backButton)
        btnBack.setOnClickListener { view: View? -> onBackPressed() }
    }
}