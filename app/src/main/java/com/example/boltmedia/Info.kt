package com.example.boltmedia

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest

class Info :AppCompatActivity() {
    lateinit var requestQueue: RequestQueue
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)
        val image = findViewById<ImageView>(R.id.imageView)
        val title = findViewById<TextView>(R.id.title)
        val rating=findViewById<TextView>(R.id.rating)
        val plot=findViewById<TextView>(R.id.overview)
        val bundle: Bundle? = intent.extras
        title.text=intent.getStringExtra("title")
        plot.text=intent.getStringExtra("plot")
        rating.text=intent.getStringExtra("rating")
        Glide.with(this).load(intent.getStringExtra("img")).into(image)

    }
}