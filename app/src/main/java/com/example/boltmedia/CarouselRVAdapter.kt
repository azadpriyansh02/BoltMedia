package com.example.boltmedia

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarouselRVAdapter(private val carouselDataList: ArrayList<Int>) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)

    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val imgView = holder.itemView.findViewById<ImageView>(R.id.imgview)
        imgView.setImageResource(carouselDataList[position])
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}