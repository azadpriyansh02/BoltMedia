package com.example.boltmedia

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)
        val image=findViewById<ImageView>(R.id.imageView)
        val textView=findViewById<TextView>(R.id.textView)
        val bundle: Bundle? = intent.extras
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(this.cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }
        var title=intent.getStringExtra("title")
        if (title != null) {
            Log.d("onCreate: ",title)
        }
        if(title=="Underverden 2"){
            title="Darkland"
        }
        val url = "http://www.omdbapi.com/?t=${title}&apikey=5dbb298b"
        Log.e( "onCreate: ",url )
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->


                    Glide.with(this).load(response.getString("Poster")).into(image)

                        val intent= Intent(this, Info::class.java)
                if(response.getString("Poster")!=null) {
                    val imgurl = response.getString("Poster")
                }
                        val plot="Plot:"+"\n\n"+response.getString("Plot")+"\n\n"
                        val rating="Rating:"+response.getString("imdbRating")
                        val name=response.getString("Title")+"\n\n"+"Writer: "+response.getString("Writer")+"\n\n"
                        val info=name+plot+rating
                textView.text=info
            },
            { error ->
                Log.d("vol",error.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)

    }
}