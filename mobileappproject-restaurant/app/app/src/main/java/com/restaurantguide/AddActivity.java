package com.restaurantguide;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // Database
    private SQLiteDatabase db;
    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Get DB
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        // Define Button Add
        Button btnAdd = findViewById(R.id.btnAdd);

        // set event for btnAdd
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set intent to RestaurantActivity
                Intent i = new Intent(v.getContext(), RestaurantActivity.class);

                EditText addName = findViewById(R.id.addName);
                EditText addAddress = findViewById(R.id.addAddress);
                EditText addPhone = findViewById(R.id.addPhone);
                EditText addDescription = findViewById(R.id.addDescription);
                EditText addTag = findViewById(R.id.addTag);
                EditText addLat = findViewById(R.id.addLatitude);
                EditText addLong = findViewById(R.id.addLongitude);

                String name = addName.getText().toString();
                String address = addAddress.getText().toString();
                String phone = addPhone.getText().toString();
                String description = addDescription.getText().toString();
                String tag = addTag.getText().toString();

                double num1 = Double.parseDouble(addLat.getText().toString());
                double num2 = Double.parseDouble(addLong.getText().toString());
                double lat = Utility.locValid(num1);
                double longitude = Utility.locValid(num2);

                if (Utility.isEmpty(name) | Utility.isEmpty(address) | Utility.isEmpty(phone) | Utility.phoneInvalid(phone)
                        | Utility.isEmpty(description) | Utility.isEmpty(tag)) {
                    toastMessage("Please fill all fields correctly.");
                } else {

                    // Add new Restaurant to DB

                    Restaurant newRestaurant = new Restaurant(name, address, phone, description, tag, lat, longitude);
                    myDB.addRestaurant(db, newRestaurant);

                    // Go back to RestaurantActivity
                    startActivity(i);
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
