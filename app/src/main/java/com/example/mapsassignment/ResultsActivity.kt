package com.example.mapsassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResultsActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private var location: String? = null
    private var selectedFragment: Fragment? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.restaurants -> {
                setLocation()
                selectedFragment = MasterFragment("restaurants", location)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        selectedFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.fuel -> {
                setLocation()
                selectedFragment = MasterFragment("fuel", location)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        selectedFragment!!).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setLocation()

        //Set the default fragment to be Restaurant
        selectedFragment = MasterFragment("restaurants", location)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                selectedFragment!!).commit()
    }

    private fun setLocation() {

        val intent = intent
        location = intent.getStringExtra("location")
    }
}