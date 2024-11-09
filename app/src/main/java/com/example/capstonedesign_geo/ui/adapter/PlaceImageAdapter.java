package com.example.capstonedesign_geo.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_geo.R;
import com.example.capstonedesign_geo.ui.fragment.NaverMapData;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PlaceImageAdapter extends RecyclerView.Adapter<PlaceImageAdapter.PlaceViewHolder> {

    private final List<NaverMapData> placeList;
    private final Function1<NaverMapData, Unit> onItemClick;

    public PlaceImageAdapter(List<NaverMapData> placeList, Function1<NaverMapData, Unit> onItemClick) {
        this.placeList = placeList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        NaverMapData place = placeList.get(position);
        holder.titleTextView.setText(place.getTitle());
        holder.categoryTextView.setText(place.getCategory());
        Glide.with(holder.itemView.getContext())
                .load(place.getFirstimage())
                .into(holder.placeImageView);

        holder.itemView.setOnClickListener(v -> onItemClick.invoke(place));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImageView;
        TextView titleTextView;
        TextView categoryTextView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImageView = itemView.findViewById(R.id.placeImage);
            titleTextView = itemView.findViewById(R.id.placeText);
            categoryTextView = itemView.findViewById(R.id.placeAddress);
        }
    }
}
