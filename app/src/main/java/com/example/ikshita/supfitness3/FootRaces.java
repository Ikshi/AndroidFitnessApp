package com.example.ikshita.supfitness3;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class FootRaces extends Fragment implements OnMapReadyCallback {

    GoogleMap myMap;
    MapView myMapView;
    View rootView;
    private LocationListener locationListener;

    private LocationManager locationManager;

    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // 5 Meters

    private LatLng latLng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tabfootraces, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myMapView = (MapView) rootView.findViewById(R.id.map);
        if (myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        myMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //GetData();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLng).title("My Position"));
                    myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //AddData(latLng.longitude,latLng.latitude);
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }


        // GetData();
       /* googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                //save Track to db
                //Double longitudeString = latLng.longitude;
                //longitudeString.toString();
                AddData(latLng.longitude,latLng.latitude);

                // Clears the previously touched position
                //myMap.clear();

                // Animating to the touched position
                myMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                myMap.addMarker(markerOptions);
                //CameraPosition myplace = CameraPosition.builder().zoom(16).bearing(0).tilt(45).build();





            }
        });

        //googleMap.addMarker(new MarkerOptions().position(new LatLng(-20.2029071,57.7201853)).title("my place").snippet("home"));

        //CameraPosition myplace = CameraPosition.builder().target(new LatLng(-20.2029071,57.7201853)).zoom(16).bearing(0).tilt(45).build();

    }*/

    public void AddData(Double longitude, Double latitude){


        DBManagerMap myDB = new  DBManagerMap(getActivity());
        boolean insertData = myDB .addData(longitude,latitude);
        if(insertData == true){
            Toast.makeText(getActivity(), "Track saved!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Error Track not saved!", Toast.LENGTH_LONG).show();
        }




    }

        //get weight from db
    public void GetData(){

        DBManagerMap myDB = new DBManagerMap(getActivity());
        MarkerOptions markerOptions = new MarkerOptions();

        Cursor data = myDB.getContents();

        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "db was empty", Toast.LENGTH_LONG).show();
        }else{
            while (data.moveToNext()){
                Double longitude = (data.getDouble(1));
                Double latitude = (data.getDouble(2));
                int id = (data.getInt(0));
                String idString = Integer.toString(id);
                System.out.println(id);
                myMap.addMarker(new MarkerOptions().position(new LatLng(longitude,latitude)).title(idString));
            }
        }
    }


    }



