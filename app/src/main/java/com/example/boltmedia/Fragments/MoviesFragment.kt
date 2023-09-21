package com.example.boltmedia.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.example.boltmedia.Adapters.movieAdapter
import com.example.boltmedia.Models.Movie
import com.example.boltmedia.R

class MoviesFragment : Fragment() {
    lateinit var requestQueue: RequestQueue
    private lateinit var view: View
    var movieList= arrayListOf<Movie>()
    var recyclerView:RecyclerView?=null
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
        recyclerView=view.findViewById(R.id.recyclerView)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    var string:String=""
                    val jsonArray=response.getJSONArray("results")
                    for(i in 0..jsonArray.length()-1){
                        val title=jsonArray.getJSONObject(i).getString("original_title")
                        val poster=jsonArray.getJSONObject(i).getString("poster_path")
                        val rating=jsonArray.getJSONObject(i).getString("vote_average")
                        var movie= Movie(poster,title,rating)
                        movieList.add(movie)
                        string+="Title:"+title+"\n"+"Poster:"+poster+"\n"
                        Log.d("Title:",title)
                        Log.d("Poster:",poster)
                    }
                    recyclerView?.layoutManager=LinearLayoutManager(context)
                    recyclerView?.adapter= movieAdapter(movieList)

                        },
                { error ->
                    Log.d("vol",error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
        return view
        }
}