package c.comp3074project.applocation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // These are the calls to the global variables.  In the actual application, Lat, Lng, and
        // Address will be based on the selected value from the database.

        Double Lat = Double.parseDouble(Global.latitude);
        Double Lng = Double.parseDouble(Global.longitude);
        String Address = Global.location;
        LatLng destination = new LatLng(Lat, Lng);

        // Sets the current location of the user.
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double sLat = location.getLatitude();
//        double sLon = location.getLongitude();
//        LatLng start = new LatLng(sLat, sLon);

        // Sets the point given to the Activity and moves the view there.
//        mMap.addMarker(new MarkerOptions().position(start).title("Start"));
        mMap.addMarker(new MarkerOptions().position(destination).title(Address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));

        //Direction test, unfinishied
//        Uri gmmIntentUri = Uri.parse("google.navigation:q="+destination);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
    }
}
