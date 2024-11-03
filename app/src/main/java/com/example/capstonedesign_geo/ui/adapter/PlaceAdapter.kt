package com.example.capstonedesign_geo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.ui.fragment.NaverMapData


class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val placeImg = itemView.findViewById<ImageView>(R.id.placeImg)
    val placeName = itemView.findViewById<TextView>(R.id.placeName)
    val placeCat = itemView.findViewById<TextView>(R.id.placeCat)
    val placeAddr = itemView.findViewById<TextView>(R.id.placeAddr)
    val placeOpen = itemView.findViewById<TextView>(R.id.placeOpen)
    val placePrefer = itemView.findViewById<ImageView>(R.id.placePrefer)
}

class PlaceAdapter(private val places: List<NaverMapData>) : RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.placeName.text = place.title
        holder.placeCat.text = place.category
        holder.placeAddr.text = place.addr1 + " " + place.addr2
        holder.placeOpen.text = place.hours


        /*Glide.with(holder.itemView.context)
            .load(place.firstimage)
            .into(holder.placeImg)*/
    }

    override fun getItemCount(): Int = places.size
}

