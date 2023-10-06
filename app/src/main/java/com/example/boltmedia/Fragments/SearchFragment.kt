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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.boltmedia.Adapters.SearchAdapter
import com.example.boltmedia.Info
import com.example.boltmedia.Models.Movie
import com.example.boltmedia.Models.Search
import com.example.boltmedia.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray


class SearchFragment : Fragment() {
    lateinit var requestQueue: RequestQueue
    var List= arrayListOf<Search>()
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
            val userinput=view.findViewById<TextInputEditText>(R.id.userinput)
            val recyclerView=view.findViewById<RecyclerView>(R.id.searchrecyclerView)
            var input = userinput.text.toString()
            val url = "http://www.omdbapi.com/?apikey=5dbb298b&s=${input}"
            Log.d("link:",url)
            val error=view.findViewById<TextView>(R.id.error)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    if(response.get("Response")=="False"){
                        userinput.visibility=View.INVISIBLE
                        search.visibility=View.INVISIBLE
                        error.text = "NO MOVIE FOUND"
                        error.visibility=View.VISIBLE
                    }else {
                        Log.e("TAG","onCreateView: "+response)
                        val jsonArray=response.getJSONArray("Search")
                        for(i in 0..jsonArray.length()-1){
                            val title=jsonArray.getJSONObject(i).getString("Title")
                            val poster=jsonArray.getJSONObject(i).getString("Poster")
                            var search=Search(poster,title)
                            List.add(search)
                            Log.d("Title:",title)
                            Log.d("Poster:",poster)


                        }
                        recyclerView.layoutManager=GridLayoutManager(context,2)
                        recyclerView.adapter=SearchAdapter(List)


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