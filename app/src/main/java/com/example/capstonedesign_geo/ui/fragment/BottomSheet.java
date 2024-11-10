package com.example.capstonedesign_geo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.NavigationActivity;
import com.example.capstonedesign_geo.ui.PolicyActivity;
import com.example.capstonedesign_geo.ui.RecommendActivity;
import com.example.capstonedesign_geo.ui.SettingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheet extends BottomSheetDialogFragment {

    private TextView tvNickname;
    private String nickname;
    private Button btnZzim, btnRecentsearch, btnRoot, btnRecommend, btnAnnouncement, btnPolicy, btnSetting;

    public BottomSheet() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // 프래그먼트가 화면에 표시될 때 호출
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false); // 프래그먼트 레이아웃을 inflate
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // 뷰가 생성될 때 호출
        super.onViewCreated(view, savedInstanceState);

        tvNickname = view.findViewById(R.id.nickname);
        getNickname();

        btnRecentsearch = view.findViewById(R.id.btnRecentsearch);
        btnRecentsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRoot = view.findViewById(R.id.btnRoot);
        btnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });


        btnRecommend = view.findViewById(R.id.btnRecommend);
        btnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);
            }
        });

        btnAnnouncement = view.findViewById(R.id.btnAnnouncement);
        btnAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPolicy = view.findViewById(R.id.btnPolicy);
        btnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PolicyActivity.class);
                startActivity(intent);
            }
        });

        btnSetting = view.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getNickname() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        nickname = sharedPreferences.getString("nickname", null);
        tvNickname.setText(nickname);
    }
}