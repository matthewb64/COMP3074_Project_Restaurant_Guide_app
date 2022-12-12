package com.restaurantguide;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class EditActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Database
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        // Get restaurantID from intent
        Intent i = getIntent();
        final long restaurantID = i.getLongExtra("restaurantID", -1);

        EditText edtName = findViewById(R.id.edtName);
        EditText edtAddress = findViewById(R.id.edtAddress);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtDescription = findViewById(R.id.edtDescription);
        EditText edtTag = findViewById(R.id.edtTag);
        EditText edtLat = findViewById(R.id.edtLatitude);
        EditText edtLong = findViewById(R.id.edtLongitude);
        RatingBar rb = findViewById(R.id.ratingBar4);

        // Get a restaurant along with restaurantID
        Restaurant r = myDB.getRestaurant(db, restaurantID);
        // Set data to features
        edtName.setText(r.getName());
        edtAddress.setText(r.getAddress());
        edtPhone.setText(r.getPhone());
        edtDescription.setText(r.getDescription());
        edtTag.setText(r.getTag());
        edtLat.setText(Double.toString(r.getLatitude()));
        edtLong.setText(Double.toString(r.getLongitude()));
        rb.setRating(r.getRating());

        // Define update button
        Button btnUpdate = findViewById(R.id.btnUpdate);
        //Set event for btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RestaurantActivity.class);

                EditText edtName1 = findViewById(R.id.edtName);
                EditText edtAddress1 = findViewById(R.id.edtAddress);
                EditText edtPhone1 = findViewById(R.id.edtPhone);
                EditText edtDescription1 = findViewById(R.id.edtDescription);
                EditText edtTag1 = findViewById(R.id.edtTag);
                EditText edtLat1 = findViewById(R.id.edtLatitude);
                EditText edtLong1 = findViewById(R.id.edtLongitude);
                RatingBar rb = findViewById(R.id.ratingBar4);


                String name = edtName1.getText().toString();
                String address = edtAddress1.getText().toString();
                String phone = edtPhone1.getText().toString();
                String description = edtDescription1.getText().toString();
                String tag = edtTag1.getText().toString();
                double lat = Double.parseDouble(edtLat1.getText().toString());
                double longitude = Double.parseDouble(edtLong1.getText().toString());
                float rating = rb.getRating();

                // Update Restaurant DB
                myDB.updateRestaurant(db, restaurantID, name, address, phone, description, tag, rating, lat, longitude);
                setResult(RESULT_OK);

                // Go back to RestaurantActivity
                startActivity(i);
            }
        });
    }
}
