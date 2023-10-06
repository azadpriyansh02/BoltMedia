package com.example.boltmedia.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.boltmedia.Info
import com.example.boltmedia.Models.Home
import com.example.boltmedia.Models.Movie
import com.example.boltmedia.R

class HomeAdapter (private val movieList:ArrayList<Home>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img1=itemView.findViewById<ImageView>(R.id.poster)
        val title=itemView.findViewById<TextView>(R.id.title)
        val rating=itemView.findViewById<TextView>(R.id.rating)
        val card=itemView.findViewById<CardView>(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val view= LayoutInflater.from(context).inflate(R.layout.movies,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie =movieList[position]
        holder.title.text=movie.title
        holder.rating.text=movie.rating
        Glide.with(context).load(movie.img).into(holder.img1)
        holder.card.setOnClickListener {
            val intent= Intent(context, Info::class.java)
            val options= Bundle()
            intent.putExtra("title",movie.title)
            intent.putExtra("plot",movie.plot)
            intent.putExtra("rating",movie.rating)
            intent.putExtra("img",movie.img)
            ContextCompat.startActivity(context, intent, options)

        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}