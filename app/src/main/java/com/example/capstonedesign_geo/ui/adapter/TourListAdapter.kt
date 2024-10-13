package com.example.capstonedesign_geo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign_geo.R
import com.example.capstonedesign_geo.data.model.Item

class TourListAdapter(
    private val items: List<Item>,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<TourListAdapter.TourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
        return TourViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.placeName)
        private val addressTextView: TextView = itemView.findViewById(R.id.placeAddr)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(item: Item) {
            titleTextView.text = item.title
            addressTextView.text = item.addr1
            if (!item.firstimage.isNullOrEmpty()) {
                Glide.with(imageView.context)
                    .load(item.firstimage)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.icon_geo)
            }
        }
    }
}
