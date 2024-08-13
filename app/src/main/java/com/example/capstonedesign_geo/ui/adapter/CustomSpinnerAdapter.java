package com.example.capstonedesign_geo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstonedesign_geo.R;

import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater inflater;
    private String text;

    public CustomSpinnerAdapter(Context context, List<String> list) {
        this.list = list;
        // LayoutInflater 객체 생성
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { // 스피너의 아이템 개수
        return (list != null) ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    } // 스피너의 아이템

    @Override
    public long getItemId(int position) {
        return position;
    } // 스피너의 아이템 아이디

    // 스피너뷰에 보여지는 텍스트뷰
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) // 뷰가 없으면 생성
            convertView = inflater.inflate(R.layout.spinner_outer_view, parent, false);
        if (list != null) { // 스피너의 아이템이 있으면
            text = list.get(position);
            ((TextView) convertView.findViewById(R.id.spinner_inner_text)).setText(text);
        }
        return convertView;
    }

    // 클릭 후 나타나는 텍스트뷰
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.spinner_inner_view, parent, false);
        if (list != null) {
            text = list.get(position);
            ((TextView) convertView.findViewById(R.id.spinner_text)).setText(text);
        }
        return convertView;
    }

    public void updateData(List<String> list) {
        this.list.clear();      // 기존 데이터 삭제
        this.list.addAll(list); // 새로운 데이터 추가
        notifyDataSetChanged(); // 데이터 변경 알림
    }
}
