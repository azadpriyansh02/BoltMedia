package com.example.boltmedia.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.boltmedia.Models.Movie
import com.example.boltmedia.R

class movieAdapter(private val movieList:ArrayList<Movie>):RecyclerView.Adapter<movieAdapter.ViewHolder>() {
    private lateinit var context:Context
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val img1=itemView.findViewById<ImageView>(R.id.poster)
        val title=itemView.findViewById<TextView>(R.id.title)
        val rating=itemView.findViewById<TextView>(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
       val view=LayoutInflater.from(context).inflate(R.layout.movies,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie =movieList[position]
        holder.title.text="TITLE:"+movie.title
        holder.rating.text="RATING:"+movie.rating
        Glide.with(context).load("https://image.tmdb.org/t/p/w92"+movie.img).into(holder.img1)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}