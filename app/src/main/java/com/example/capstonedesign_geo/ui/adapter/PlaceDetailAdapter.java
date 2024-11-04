package com.example.capstonedesign_geo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;

import java.util.List;

public class PlaceDetailAdapter extends RecyclerView.Adapter<PlaceDetailAdapter.ViewHolder> {

    private final List<NaverMapData> dataList;
    private boolean showAll = false;

    public PlaceDetailAdapter(List<NaverMapData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NaverMapData data = dataList.get(position);

        //holder.icon.setImageResource(data.());
        holder.title.setText(data.getTitle());
        holder.description.setText(data.describeContents());

        if (!showAll && position >= 3) {
            holder.itemView.setAlpha(0.5f);
        } else {
            holder.itemView.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return showAll ? dataList.size() : Math.min(4, dataList.size());
    }

    public void showAllItems() {
        showAll = true;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.leftIcon);
            title = itemView.findViewById(R.id.topic);
            description = itemView.findViewById(R.id.description);
        }
    }
}
