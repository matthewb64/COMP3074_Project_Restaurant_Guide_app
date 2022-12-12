package com.restaurantguide;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantDetailActivity extends AppCompatActivity implements SensorEventListener {

    Button btnEdit, btnShowMap, btnEmail;
    private RatingBar rating_bar;
    // Define DB
    private SQLiteDatabase db;
    private DBHelper myDB;

    // Setting sensorManager for location tracking
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // DB instantiation
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        // Get restaurantID from intent
        Intent i = getIntent();
        final long restaurantID = i.getLongExtra("restaurantID", -1);

        // Defining TextView
        TextView txtName = findViewById(R.id.txtName);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtPhone = findViewById(R.id.txtPhone);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtTag = findViewById(R.id.txtTag);
        TextView txtLatitude = findViewById(R.id.txtLatitude);
        TextView txtLongitude = findViewById(R.id.txtLongitude);

        // Set database value for TextView
        Restaurant r = myDB.getRestaurant(db, restaurantID);
        txtName.setText(r.getName());
        txtAddress.setText(r.getAddress());
        txtPhone.setText(r.getPhone());
        txtDescription.setText(r.getDescription());
        txtTag.setText(r.getTag());
        txtLatitude.setText(String.valueOf(r.getLatitude()));
        txtLongitude.setText(String.valueOf(r.getLongitude()));

        //Rating Bar
        rating_bar = findViewById(R.id.ratingBar3);
        rating_bar.setRating(r.getRating());
        rating_bar.setEnabled(false);

        //Defining Button
        btnEdit = findViewById(R.id.btnEdit);
        // set even for btnEdit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditActivity.class);
                i.putExtra("restaurantID", restaurantID);
                startActivity(i);
            }
        });

        // Activating sensor service for map
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Defining Map Button
        btnShowMap = findViewById(R.id.btnShowMap);
        // set event for btnMap
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                i.putExtra("restaurantID", restaurantID);
                startActivity(i);
            }
        });

        //Defining Email Button
        btnEmail = findViewById(R.id.btnEmail);
        // set event for btnEmail
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EmailActivity.class);
                i.putExtra("restaurantID", restaurantID);
                startActivity(i);
            }
        });
    }


    // Required Sensor override activities
    @Override
    protected void onResume() {
        // Resumes tracker when app is running/tracking is available
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Pauses tracker when necessary
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
