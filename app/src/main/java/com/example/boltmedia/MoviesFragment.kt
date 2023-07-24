package com.example.boltmedia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.bumptech.glide.Glide
import org.json.JSONArray

class MoviesFragment : Fragment() {
    lateinit var requestQueue: RequestQueue
    private lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies, container, false)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(requireContext().cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }
        val url="https://api.themoviedb.org/3/discover/movie?api_key=483cafeb1a5940078ecba0384f5b9ea2"
        val display=view.findViewById<Button>(R.id.display)
        val movies=view.findViewById<TextView>(R.id.movies)

        display.setOnClickListener(){
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    var string:String=""
                    val jsonArray=response.getJSONArray("results")
                    for(i in 0..jsonArray.length()-1){
                        val title=jsonArray.getJSONObject(i).getString("original_title")
                        val plot=jsonArray.getJSONObject(i).getString("overview")
                        val rating=jsonArray.getJSONObject(i).getString("vote_average")
                        string+="Title:"+title+"\n"+"Plot:"+plot+"\n"+"Rating:"+rating+"\n"
                        Log.d("Title:",title)
                        Log.d("Plot:",plot)
                        Log.d("Rating:",rating)
                    }
                    movies.text=string
                },
                { error ->
                    Log.d("vol",error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
            display.visibility=View.INVISIBLE
        }
        return view
        }
}