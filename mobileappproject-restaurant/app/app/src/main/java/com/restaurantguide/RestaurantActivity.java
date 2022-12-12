package com.restaurantguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.text.TextUtils;
import android.widget.SearchView;


public class RestaurantActivity extends AppCompatActivity {

    Button btnAdd;
    private ArrayList<Restaurant> restaurants;
    private RestaurantAdapter restaurantsAdapter;
    private ListView lvRestaurants;

    // Database
    private SQLiteDatabase db;
    private DBHelper myDB;

    // Define SearchView
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Get Database
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        lvRestaurants = findViewById(R.id.restaurantsList);
        restaurants = new ArrayList<>();

        // Add restaurants in DB to ArrayList restaurants
        Cursor data = myDB.getAllRestaurants(db);
        while(data.moveToNext()){
            Restaurant r = myDB.getRestaurant(db, Long.parseLong(data.getString(0)));
            restaurants.add(r);
        }

        // Apply restaurants to adapter
        restaurantsAdapter = new RestaurantAdapter(this, R.layout.custom_layout, restaurants);
        lvRestaurants.setAdapter(restaurantsAdapter);

        // Defining Button Add
        btnAdd = findViewById(R.id.btnAdd);

        // Set Click Listener for triggering Adding Restaurant Activity
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddActivity.class);
                startActivity(i);
            }
        });

        // Set Long Click Listener for removing restaurant
        lvRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext()).setTitle("Warning!")
                        .setMessage("Do you want to remove this restaurant ?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get restaurant based on position on the ArrayList restaurants
                                Restaurant r = (Restaurant) parent.getItemAtPosition(position);
                                // Remove from ListView
                                restaurants.remove(r);
                                // Remove in DB
                                myDB.removeRestaurant(db, r);

                                restaurantsAdapter.notifyDataSetChanged();
                            }
                        }).show();
                return true;
            }
        });
        // set event for item click
        lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go to RestaurantDetailActivity
                Intent i = new Intent(view.getContext(), RestaurantDetailActivity.class);
                i.putExtra("position", position);
                i.putExtra("restaurantID", restaurants.get(position).getId());
                startActivity(i);
            }
        });

        // Filter
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    restaurantsAdapter.filter("");
                    lvRestaurants.clearTextFilter();
                } else {
                    restaurantsAdapter.filter(newText);
                }
                return true;
            }
        });
    }
}
