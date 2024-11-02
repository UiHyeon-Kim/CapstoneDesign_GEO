package com.example.capstonedesign_geo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.data.model.Place;
import com.example.capstonedesign_geo.ui.adapter.PlaceAdapter;
import com.example.capstonedesign_geo.utility.StatusBarKt;
import com.example.capstonedesign_geo.viewmodel.UserViewModel;

import java.util.ArrayList;

public class PreferenceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private ArrayList<Place> placeList;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_list);
        //StatusBarKt.setStatusBarColor(this, R.color.mainblue);
        StatusBarKt.setStatusBarColor(this, getResources().getColor(R.color.mainblue));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeList = new ArrayList<>();

        placeList.add(new Place("","https://example.com/image1.jpg", "이름", "카테고리", "주소", "오픈"));

        //adapter = new PlaceAdapter(placeList);
        recyclerView.setAdapter(adapter);
    }
}
