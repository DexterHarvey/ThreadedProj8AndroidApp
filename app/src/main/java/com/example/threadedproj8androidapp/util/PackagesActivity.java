package com.example.threadedproj8androidapp.util;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.threadedproj8androidapp.adapter.PackagesAdapter;
import com.example.threadedproj8androidapp.model.PkgDestinationsEntity;
import com.example.threadedproj8androidapp.model.CustomerEntity;
import com.example.threadedproj8androidapp.model.PackageEntity;

import com.example.threadedproj8androidapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.threadedproj8androidapp.databinding.ActivityMapsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;

public class PackagesActivity extends FragmentActivity implements OnMapReadyCallback, PackagesAdapter.OnItemClicked {

    private GoogleMap mMap;
    private ActivityMapsBinding mapsBinding;
//    ListView listView;
    RequestQueue queue;
    RecyclerView rvPackages;
    CustomerEntity customer;

    PackageEntity selectedPackage; // holder for last clicked package
    private ArrayList<PkgDestinationsEntity> coordsArray;
    private PackagesAdapter adapter;

    // Holds color sets for package markers
    private final int[] MARKER_COLOURS = {1, 50, 100, 180, 240, 280, 30, 320, 80};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        customer = (CustomerEntity) intent.getSerializableExtra("customer");
        mapsBinding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(mapsBinding.getRoot());

//        listView = findViewById(R.id.packages_lvPackages);
//        btnDetails = findViewById(R.id.packages_btnDetails);
        rvPackages = findViewById(R.id.rvPackages);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get queue and make request for packages
        queue = Volley.newRequestQueue(getApplicationContext());

        Executors.newSingleThreadExecutor().execute(new GetCoordinates()); // see method for details

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set up is all taken care of in the runnable threads (since marker placement requires some db calls)
        // and in the xml layout

    }

    // On package click, we zoom to the coords in that package
    @Override
    public void onItemClick(int position) {

        // Grab id from adapter based on click position
        int pkgID = adapter.getPackages().get(position).getPackageId();

        // Begin constructing LatLngBounds - this is an area of map that covers all given coordinates
        LatLngBounds.Builder zoneBuilder = new LatLngBounds.Builder();

        int coordsCounter = 0; // we want to track whether there's only one coordinate - zooming is weird in this case
        LatLng currentCoords = null;
        for (PkgDestinationsEntity coords : coordsArray){
            // Each set of coords part of this package is added to the bounds
            if (coords.getPackageId() == pkgID){
                currentCoords = new LatLng(coords.getLatitude(), coords.getLongitude());
                zoneBuilder.include(currentCoords);
                coordsCounter ++;
            }
        }
        //Special case if NO destinations - just pass over this to prevent crashing!
        if(coordsCounter == 0){
            return;
        }
        // Special case if just one set of coords - just go to it
        if (coordsCounter == 1) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoords, 8));
        }
        // Otherwise, zoom to the region containing all coords
        else {
            LatLngBounds packageZone = zoneBuilder.build();

            // Pan and zoom over to the package region
            // Thanks to this link for help getting the zoom right https://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.4); // 0.4 represents the rough 40% of the phone height the map takes up.. rough measure
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(packageZone, width, height, (int) (width * 0.10)));
        }
    }


    private class GetCoordinates implements Runnable{

        @Override
        public void run() {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                    "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/destinations/getall",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Put the results in an array for later use
                            coordsArray = new ArrayList<>();
                            try {
                                for(int i = 0; i< response.length(); i++){
                                    // Grab the current Package object from the array
                                    PkgDestinationsEntity coords = new Gson().fromJson(response.getString(i), PkgDestinationsEntity.class);
                                    // Add the package to the listview adapter
                                    coordsArray.add(coords);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        // Proceed to run the GetPackages method
                        Executors.newSingleThreadExecutor().execute(new GetPackages2()); // see method for details

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PackagesActivity.this, "Failed to connect to database. Please try again or contact customer service",
                            Toast.LENGTH_LONG).show();
                }
            });
            queue.add(req);
        }
    }

    private class GetPackages2 implements Runnable {
        ArrayList<PackageEntity> packages = new ArrayList<PackageEntity>();
        @Override
        public void run() {
            // Create StringRequest, needs method, url, listener for what to do if success, listener for what to do if failure
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                    "http://10.0.2.2:8080/RESTApiForAndroid_war_exploded/api/packages/getallupcoming",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Create an array adapter for incoming listview data
                            try {
                                // Date parsing is a real delight. We need a custom GSON object to handle the incoming date strings.
                                //TODO: my bugvision suggests this might mess up if ever packages get added with differently formatted dates in the javafx app...
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.setDateFormat("MMM dd, yyyy, HH:mm:ss aaa");
                                Gson gson = gsonBuilder.create();
                                boolean firstMarker = true; // we're going to move the map to focus on just the first marker, so need to track this
                                // Use Gson to pull out each JsonObject and add it to adapter
                                for(int i = 0; i < response.length(); i++){
                                    // Grab the current Package object from the array
                                    PackageEntity pkg = gson.fromJson(response.getString(i), PackageEntity.class);
                                    // Add the package to the list of packages
                                    packages.add(pkg);

                                    // Add markers on map for the package. requires addition of LatLng to db.
                                    // Denote a marker color for this particular package
                                    int colorVal;
                                    if (i < MARKER_COLOURS.length) { colorVal = MARKER_COLOURS[i]; } // we'll use a preset one if enough
                                    else { colorVal = new Random().nextInt(361); } // otherwise get a random colour

                                    // Go through the list of coordinates and put ones corresponding to this package on the map
                                    for(PkgDestinationsEntity coordsSet: coordsArray ){
                                        if (coordsSet.getPackageId() == pkg.getPackageId()){
                                            // Do a one-time focus of the map on marked location location
                                            if(firstMarker == true){
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(coordsSet.getLatitude(), coordsSet.getLongitude())));
                                                firstMarker = false;
                                            }
                                            // Add the marker
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(coordsSet.getLatitude(), coordsSet.getLongitude()))
                                                    .title(coordsSet.getName() + " - " + pkg.getPkgName())
                                                    .snippet(coordsSet.getDescription())
                                                    .icon(BitmapDescriptorFactory.defaultMarker(colorVal)));

                                        }
                                    }
                                }
                                // We're in an async thread, and to edit the UI in such a place, we need to use the UI thread.
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter = new PackagesAdapter(getApplicationContext(), packages, customer);
                                        rvPackages.setAdapter(adapter);
                                        rvPackages.setLayoutManager(new LinearLayoutManager(PackagesActivity.super.getApplicationContext()));
                                        rvPackages.setNestedScrollingEnabled(false);
                                        adapter.setOnClick(PackagesActivity.this);
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
