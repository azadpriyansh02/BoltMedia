package com.example.boltmedia.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.example.boltmedia.Adapters.tvAdapter
import com.example.boltmedia.Models.TvShow
import com.example.boltmedia.R

class TvshowsFragment : Fragment() {

    lateinit var requestQueue: RequestQueue
    private lateinit var view: View
    var tvShowList= arrayListOf<TvShow>()
    var recyclerView:RecyclerView?=null
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
            recyclerView=view.findViewById(R.id.tvRecyclerView)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    var string:String=""
                    val jsonArray=response.getJSONArray("results")
                    for(i in 0..jsonArray.length()-1){
                        val title=jsonArray.getJSONObject(i).getString("original_name")
                        val poster=jsonArray.getJSONObject(i).getString("poster_path")
                        val rating=jsonArray.getJSONObject(i).getString("vote_average")
                        Log.d("Title:",title)
                        Log.d("Poster:",poster)
                        Log.d("Rating:",rating)
                        string+="Title:"+title+"\n"+"Plot:"+poster+"\n"+"Rating:"+rating+"\n"
                        var tvShow= TvShow(poster,title,rating)
                        tvShowList.add(tvShow)
                    }
                    recyclerView?.layoutManager= GridLayoutManager(context,4)
                    recyclerView?.adapter= tvAdapter(tvShowList)
                },
                { error ->
                    Log.d("vol",error.toString())
                }
            )
            requestQueue.add(jsonObjectRequest)
        return view
    }
}
