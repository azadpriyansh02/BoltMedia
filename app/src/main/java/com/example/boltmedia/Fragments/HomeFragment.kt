package com.example.boltmedia.Fragments
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.example.boltmedia.Adapters.CarouselRVAdapter
import com.example.boltmedia.Adapters.HomeAdapter
import com.example.boltmedia.Models.Home
import com.example.boltmedia.R


class HomeFragment : Fragment() {
    private lateinit var view: View
    var movies= arrayListOf<Home>()
    lateinit var requestQueue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val recyclerView=view.findViewById<RecyclerView>(R.id.homeRecyclerView)
        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
            val demoData = arrayListOf(
                R.drawable.shazam1,
                R.drawable.mario,
                R.drawable.the_popes_exorcist,
                R.drawable.ant_manandthewaspquantumaniaslidem,
                R.drawable.ie_100131
            )

            viewPager.adapter = CarouselRVAdapter(demoData)
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = (0.80f + r * 0.20f)
            }
            viewPager.setPageTransformer(compositePageTransformer)
            val appnetwork = BasicNetwork(HurlStack())
            val appcache = DiskBasedCache(requireContext().cacheDir, 1024 * 1024) // 1MB cap
            requestQueue = RequestQueue(appcache, appnetwork).apply {
                start()
            }
            val url="https://apex.oracle.com/pls/apex/azad_priyansh02/movies/home"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        var string:String=""
                        val jsonArray=response.getJSONArray("items")
                        for(i in 0..jsonArray.length()-1){
                            val title=jsonArray.getJSONObject(i).getString("title")
                            val plot=jsonArray.getJSONObject(i).getString("plot")
                            val rating=jsonArray.getJSONObject(i).getString("rating")
                            val poster=jsonArray.getJSONObject(i).getString("poster")
                            Log.d("Title:",title)
                            Log.d("Plot:",plot)
                            Log.d("Rating:",rating)
                            Log.d("Poster:",poster)
                            var movie=Home(poster,title,rating,plot)
                            movies.add(movie)


                        }
                        recyclerView?.layoutManager=LinearLayoutManager(context)
                        recyclerView?.adapter=HomeAdapter(movies)

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


