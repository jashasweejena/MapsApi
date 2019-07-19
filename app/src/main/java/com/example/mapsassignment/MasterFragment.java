package com.example.mapsassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter_extensions.drag.ItemTouchCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class MasterFragment extends Fragment implements ItemTouchCallback {
    private final String fuelURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=gas+stations+in+%s&key=YOUR_API_KEY&&fields=formatted_address,geometry";
    private final String restURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Restaurants+in+%s&key=YOUR_API_KEY&&fields=formatted_address,geometry";
    private String location;
    private RequestQueue queue;
    private String type;
    private String URL;
    private RecyclerView recyclerView;
    private FastAdapter<LocationModel> fastAdapter;
    private ItemAdapter<LocationModel> itemAdapter;

    public MasterFragment(String type, @Nullable String location) {
        this.type = type;
        if (type.equals("fuel")) {
            URL = fuelURL;
        } else {
            URL = restURL;
        }

        this.location = location;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_layout, container, false);
        getLatLng(location);

        recyclerView = fragmentView.findViewById(R.id.recycler);
        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fastAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            String lat = item.getLat();
            String lng = item.getLng();
            Uri gmmIntentUri = Uri.parse(String.format("geo:%s,%s?q=%s,%s ", lat, lng, lat, lng));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(mapIntent);
            }
            return false;
        });
        return fragmentView;
    }

    @Override
    public boolean itemTouchOnMove(int oldPosition, int newPosition) {
        return false;
    }

    @Override
    public void itemTouchDropped(int oldPosition, int newPosition) {

    }

    private void getLatLng(@Nullable String city) {
        String localURL = URL;

        city = city.replace(' ', '+');
        if (city != null) {
            localURL = String.format(localURL, city); //To insert city in URL
        } else {
            localURL = String.format(localURL, "Bangalore"); //Default city is set to Bangalore
        }

        queue = Volley.newRequestQueue(getContext());

        StringRequest gasRequest = new StringRequest(Request.Method.GET, localURL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray results = jsonObject.getJSONArray("results");
                String status = jsonObject.getString("status");
                if (status.equals("OK")) {
                    for (int i = 0; i < results.length() - 1; i++) {
                        JSONObject result = (JSONObject) results.get(i);
                        JSONObject geometry = result.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        String lat = location.getString("lat");
                        String lng = location.getString("lng");
                        String name = result.get("name").toString();

                        LocationModel model = new LocationModel(name, type, lat, lng);
                        itemAdapter.add(model);
                        fastAdapter.notifyAdapterDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Please Try again in 1 minute", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Log.d(TAG, "onErrorResponse: " + error);
            error.printStackTrace();
            Toast.makeText(getContext(), "That did not work", Toast.LENGTH_SHORT).show();
        });

        queue.add(gasRequest);
    }


}
