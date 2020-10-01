package com.example.mapsassignment

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.OnClickListener
import com.mikepenz.fastadapter_extensions.drag.ItemTouchCallback
import org.json.JSONException
import org.json.JSONObject

class MasterFragment(private val type: String, location: String?) : Fragment(), ItemTouchCallback {
    private val fuelURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=gas+stations+in+%s&key=YOUR_API_KEY&&fields=formatted_address,geometry"
    private val restURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Restaurants+in+%s&key=YOUR_API_KEY&&fields=formatted_address,geometry"
    private val location: String?
    private var queue: RequestQueue? = null
    private var URL: String? = null
    private var recyclerView: RecyclerView? = null
    private var fastAdapter: FastAdapter<LocationModel>? = null
    private var itemAdapter: ItemAdapter<LocationModel>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_layout, container, false)
        getLatLng(location)
        recyclerView = fragmentView.findViewById(R.id.recycler)
        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)
        recyclerView!!.setLayoutManager(LinearLayoutManager(context))
        recyclerView!!.setAdapter(fastAdapter)
        recyclerView!!.setNestedScrollingEnabled(false)
        fastAdapter!!.withOnClickListener(OnClickListener { v: View?, adapter: IAdapter<LocationModel>?, item: LocationModel, position: Int ->
            val lat = item.lat
            val lng = item.lng
            val gmmIntentUri = Uri.parse(String.format("geo:%s,%s?q=%s,%s ", lat, lng, lat, lng))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context!!.packageManager) != null) {
                startActivity(mapIntent)
            }
            false
        })
        return fragmentView
    }

    override fun itemTouchOnMove(oldPosition: Int, newPosition: Int): Boolean {
        return false
    }

    override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {}
    private fun getLatLng(city: String?) {
        var city = city
        var localURL = URL
        city = city!!.replace(' ', '+')
        localURL = if (city != null) {
            String.format(localURL!!, city) //To insert city in URL
        } else {
            String.format(localURL!!, "Bangalore") //Default city is set to Bangalore
        }
        queue = Volley.newRequestQueue(context)
        val gasRequest = StringRequest(Request.Method.GET, localURL, { response: String? ->
            try {
                val jsonObject = JSONObject(response)
                val results = jsonObject.getJSONArray("results")
                val status = jsonObject.getString("status")
                if (status == "OK") {
                    for (i in 0 until results.length() - 1) {
                        val result = results[i] as JSONObject
                        val geometry = result.getJSONObject("geometry")
                        val location = geometry.getJSONObject("location")
                        val lat = location.getString("lat")
                        val lng = location.getString("lng")
                        val name = result["name"].toString()
                        val model = LocationModel(name, type, lat, lng)
                        itemAdapter!!.add(model)
                        fastAdapter!!.notifyAdapterDataSetChanged()
                    }
                } else {
                    Toast.makeText(context, "Please Try again in 1 minute", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error: VolleyError ->
            Log.d(ContentValues.TAG, "onErrorResponse: $error")
            error.printStackTrace()
            Toast.makeText(context, "That did not work", Toast.LENGTH_SHORT).show()
        }
        queue!!.add(gasRequest)
    }

    init {
        URL = if (type == "fuel") {
            fuelURL
        } else {
            restURL
        }
        this.location = location
    }
}