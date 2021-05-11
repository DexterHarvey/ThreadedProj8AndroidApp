package com.example.threadedproj8androidapp;

import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.threadedproj8androidapp.Model.PackageEntity;
import com.example.threadedproj8androidapp.databinding.ActivityStartBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.threadedproj8androidapp.databinding.ActivityMapsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.concurrent.Executors;

public class PackagesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding mapsBinding;
    ListView listView;
    RequestQueue queue;

    PackageEntity selectedPackage; // holder for last clicked package

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapsBinding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(mapsBinding.getRoot());

        listView = findViewById(R.id.packages_lvPackages);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get queue and make request for packages
        queue = Volley.newRequestQueue(getApplicationContext());

        Executors.newSingleThreadExecutor().execute(new GetPackages()); // see method for details

        // todo: Have it so that clicking a package sets a class variable to it,
        //  to pass on to a Package Details intent on button press
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPackage = (PackageEntity) listView.getItemAtPosition(position);
            // todo: highlight listview item, zoom to map position
            Toast.makeText(this, "test: " + selectedPackage.getPkgName(), Toast.LENGTH_SHORT).show();
        });

        // todo: define button behaviour to go to new intent (with selected package data)





    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Gets all package data from API and loads it into the listview.
     */
    private class GetPackages implements Runnable {
        @Override
        public void run() {
            // Create StringRequest, needs method, url, listener for what to do if success, listener for what to do if failure
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                    "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/packages/getall",
                    null,
                    new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Create an array adapter for incoming listview data
                    ArrayAdapter<PackageEntity> arrayAdapter = new ArrayAdapter<PackageEntity>(getApplicationContext(), android.R.layout.simple_list_item_1);
                    try {
                        // Date parsing is a real delight. We need a custom GSON object to handle the incoming date strings.
                        //TODO: my bugvision suggests this might mess up if ever packages get added with differently formatted dates in the javafx app...
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("MMM dd, yyyy, HH:mm:ss aaa");
                        Gson gson = gsonBuilder.create();
                        // Use Gson to pull out each JsonObject and add it to adapter
                        for(int i = 0; i< response.length(); i++){
                            // Grab the current Package object from the array
                            PackageEntity pkg = gson.fromJson(response.getString(i), PackageEntity.class);
                            // Add the package to the listview adapter
                            arrayAdapter.add(pkg);
                            // TODO: add a marker on map for the package. requires addition of LatLng to db
                            if(pkg.getPackageId() == 2){
                                mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(23.8, -82.23))
                                    .title(pkg.getPkgName()));
                            }
                        }
                        // We're in an async thread, and to edit the UI in such a place, we need to use the UI thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(arrayAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PackagesActivity.this, "Failed to connect to database. Please try again or contact customer service",
                            Toast.LENGTH_LONG).show();
                }
            });

            // This is hiding but very important! Actually attaches the request to the queue that does the calling
            queue.add(req);
        }
    }
}
