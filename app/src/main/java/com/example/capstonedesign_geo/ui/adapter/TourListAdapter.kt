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
    private var items: List<Item>,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<TourListAdapter.TourViewHolder>() {

    inner class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            // UI에 데이터 바인딩 처리 (예: 텍스트뷰에 데이터 설정)
            itemView.findViewById<TextView>(R.id.placeName).text = item.title
            itemView.findViewById<TextView>(R.id.placeAddr).text = item.addr1
            // 이미지 로딩 처리 (예: Glide 사용)
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.icon_geo)
            if (!item.firstimage.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(item.firstimage)
                    .into(itemView.findViewById(R.id.imageView))
            }

            // 클릭 이벤트 처리
            itemView.setOnClickListener {
                //onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
        return TourViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        /*val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }*/
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /*class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
    }*/

    // 리스트 업데이트 함수
    fun updateItems(newItems: List<Item>) {
        this.items = newItems
        notifyDataSetChanged()  // 데이터가 변경되었음을 어댑터에 알림
    }
}
