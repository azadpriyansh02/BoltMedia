package com.example.boltmedia
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.boltmedia.Fragments.HomeFragment
import com.example.boltmedia.Fragments.MoviesFragment
import com.example.boltmedia.Fragments.SearchFragment
import com.example.boltmedia.Fragments.TvshowsFragment
import com.example.boltmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomnav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(HomeFragment())
                R.id.movies -> replaceFragment(MoviesFragment())
                R.id.tvshows -> replaceFragment(TvshowsFragment())
                R.id.kids -> replaceFragment(SearchFragment())
                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()


    }
}