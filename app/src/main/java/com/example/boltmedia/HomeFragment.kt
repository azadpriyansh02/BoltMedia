package com.example.boltmedia
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
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


class HomeFragment : Fragment() {
    private lateinit var view: View
    lateinit var requestQueue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)

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
            val url="https://api.themoviedb.org/3/discover/tv?api_key=483cafeb1a5940078ecba0384f5b9ea2"
            val display=view.findViewById<Button>(R.id.display)
            val display1=view.findViewById<Button>(R.id.display1)
            val movies=view.findViewById<TextView>(R.id.tvshows)
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
                        movies.text=string
                    },
                    { error ->
                        Log.d("vol",error.toString())
                    }
                )

                requestQueue.add(jsonObjectRequest)
            }
            val url1="https://api.themoviedb.org/3/discover/movie?api_key=483cafeb1a5940078ecba0384f5b9ea2"

            display1.setOnClickListener(){
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url1, null,
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
            }
            return view

        }
    }
}


