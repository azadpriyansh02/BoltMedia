package com.example.boltmedia

import com.bumptech.glide.Glide
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Info :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)
        val image=findViewById<ImageView>(R.id.imageView)
        val textView=findViewById<TextView>(R.id.textView)
        val bundle: Bundle? = intent.extras
        val url: String? = intent.getStringExtra("url")
        val info=intent.getStringExtra("info")
        Glide.with(this).load(url).into(image)
        textView.text=info


    }
}