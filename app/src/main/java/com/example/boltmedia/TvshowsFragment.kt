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
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest

class TvshowsFragment : Fragment() {

    lateinit var requestQueue: RequestQueue
    private lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_tvshows, container, false)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(requireContext().cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }
        val url="https://api.themoviedb.org/3/discover/tv?api_key=483cafeb1a5940078ecba0384f5b9ea2"
        val display=view.findViewById<Button>(R.id.display)
        val tvshows=view.findViewById<TextView>(R.id.tvshows)
        display.setOnClickListener(){
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    var string:String=""
                    val jsonArray=response.getJSONArray("results")
                    for(i in 0..jsonArray.length()-1){
                        val title=jsonArray.getJSONObject(i).getString("original_name")
                        val plot=jsonArray.getJSONObject(i).getString("overview")
                        val rating=jsonArray.getJSONObject(i).getString("vote_average")
                        Log.d("Title:",title)
                        Log.d("Plot:",plot)
                        Log.d("Rating:",rating)
                        string+="Title:"+title+"\n"+"Plot:"+plot+"\n"+"Rating:"+rating+"\n"

                    }
                    tvshows.text=string
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
