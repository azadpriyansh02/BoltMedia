package com.example.boltmedia.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.boltmedia.Info
import com.example.boltmedia.R


class SearchFragment : Fragment() {
    lateinit var requestQueue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_search, container, false)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(requireContext().cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        val search=view.findViewById<Button>(R.id.search)
        search.setOnClickListener {
            val userinput=view.findViewById<EditText>(R.id.userinput)
            var input = userinput.text.toString()
            val search=view.findViewById<Button>(R.id.search)
            val url = "http://www.omdbapi.com/?t=${input}&apikey=5dbb298b"
            Log.d("link:",url)
            val card=view.findViewById<CardView>(R.id.card)
            val name=view.findViewById<TextView>(R.id.name)
            val plot=view.findViewById<TextView>(R.id.plot)
            val image=view.findViewById<ImageView>(R.id.image)
            val error=view.findViewById<TextView>(R.id.error)
            val rating=view.findViewById<TextView>(R.id.rating)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    if(response.get("Response")=="False"){
                        userinput.visibility=View.INVISIBLE
                        search.visibility=View.INVISIBLE
                        error.text = "NO MOVIE FOUND"
                        error.visibility=View.VISIBLE
                    }else {
                        userinput.visibility=View.INVISIBLE
                        search.visibility=View.INVISIBLE
                        card.visibility=View.VISIBLE
                        Glide.with(this).load(response.getString("Poster")).into(image)
                        plot.text = "Plot:"+"\n\n"+response.getString("Plot")+"\n\n"
                        rating.text= "Rating:"+response.getString("imdbRating")
                        name.text = response.getString("Title")+"\n\n"+"Writer: "+response.getString("Writer")+"\n\n"
                        card.setOnClickListener {
                            val intent=Intent(context, Info::class.java)
                            val imgurl=response.getString("Poster")
                            val plot="Plot:"+"\n\n"+response.getString("Plot")+"\n\n"
                            val rating="Rating:"+response.getString("imdbRating")
                            val name=response.getString("Title")+"\n\n"+"Writer: "+response.getString("Writer")+"\n\n"
                            intent.putExtra("url",imgurl)
                            intent.putExtra("info",name+plot+rating)
                            startActivity(intent)
                        }
                    }
                },
                { error ->
                    Log.d("vol",error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
        }
    return view
    }
}