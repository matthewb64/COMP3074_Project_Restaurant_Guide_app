package com.restaurantguide;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    Button btnDirections;
    // Define DB
    private SQLiteDatabase db;
    private DBHelper myDB;

    private LocationManager locationManager;

    private static final int LOCATION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //         DB instantiation
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        //Defining Button
        btnDirections = findViewById(R.id.btnDirections);
        // set event for btnEdit
        btnDirections.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get restaurantID from intent
                Intent i = getIntent();
                final long restaurantID = i.getLongExtra("restaurantID", -1);
                Restaurant r = myDB.getRestaurant(db, restaurantID);

                // Get Lat/Lng from database
                String Lat = String.valueOf(r.getLatitude());
                String Lng = String.valueOf(r.getLongitude());

                // Sends request to google maps
                Uri directions = Uri.parse("google.navigation:q="+ Lat + "," + Lng);
                i = new Intent(Intent.ACTION_VIEW, directions);
                startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//         DB instantiation
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();
        // Get restaurantID from intent
        Intent i = getIntent();
        final long restaurantID = i.getLongExtra("restaurantID", -1);
        Restaurant r = myDB.getRestaurant(db, restaurantID);
        // Set Address variables from database
        String Address = r.getName();
        Double Lat = r.getLatitude();
        Double Lng = r.getLongitude();
        LatLng Destination = new LatLng(Lat, Lng);

        // Places markers for start and end locations on map
        mMap.addMarker(new MarkerOptions().position(Destination).title(Address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Destination));

        // Draws line between locations
        polyline = mMap.addPolyline(new PolylineOptions().add(Destination));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("LOCATION", "Permission not granted");
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0, 0, this
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    Polyline polyline;

    @Override
    public void onLocationChanged(Location location) {

        if(mMap!=null){

            // Checks for change in phone location
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions mark = new MarkerOptions().position(pos).title("Your location");
            mMap.addMarker(mark);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
            List l = polyline.getPoints();
            l.add(pos);
            polyline.setPoints(l);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
