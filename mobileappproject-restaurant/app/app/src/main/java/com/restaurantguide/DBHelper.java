package com.restaurantguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = ".db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RestaurantContract.SQL_CREATE_RESTAURANT);

        addRestaurant(db, new Restaurant("Hotel One", "Adress", "1702917289", "Description for this hotel", "It have all food", 5, 13.654124, -29.401534));
        addRestaurant(db, new Restaurant("Hotel two", "Adress", "1702917289", "Description for this hotel", "It have all food", 5, 13.654124, -29.401534));
        addRestaurant(db, new Restaurant("Hotel Three", "Adress", "1702917289", "Description for this hotel", "It have all food", 5, 13.654124, -29.401534));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RestaurantContract.SQL_DROP_RESTAURANT);
        onCreate(db);
    }

    public long addRestaurant(SQLiteDatabase db, Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.NAME, restaurant.getName());
        values.put(RestaurantContract.RestaurantEntry.ADDRESS, restaurant.getAddress());
        values.put(RestaurantContract.RestaurantEntry.PHONE, restaurant.getPhone());
        values.put(RestaurantContract.RestaurantEntry.DESCRIPTION, restaurant.getDescription());
        values.put(RestaurantContract.RestaurantEntry.TAG, restaurant.getTag());
        values.put(RestaurantContract.RestaurantEntry.RATING, restaurant.getRating());
        values.put(RestaurantContract.RestaurantEntry.LATITUDE, restaurant.getLatitude());
        values.put(RestaurantContract.RestaurantEntry.LONGITUDE, restaurant.getLongitude());

        return db.insert(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
    }

    public Restaurant getRestaurant(SQLiteDatabase db, long restaurantId){
        String[] projection = {
                RestaurantContract.RestaurantEntry._ID,
                RestaurantContract.RestaurantEntry.NAME,
                RestaurantContract.RestaurantEntry.ADDRESS,
                RestaurantContract.RestaurantEntry.PHONE,
                RestaurantContract.RestaurantEntry.DESCRIPTION,
                RestaurantContract.RestaurantEntry.TAG,
                RestaurantContract.RestaurantEntry.RATING,
                RestaurantContract.RestaurantEntry.LATITUDE,
                RestaurantContract.RestaurantEntry.LONGITUDE
        };
        String selection = RestaurantContract.RestaurantEntry._ID+"= ? ";
        String[] selectionArgs = {Long.toString(restaurantId)};

        Cursor cursor = db.query(
                RestaurantContract.RestaurantEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry._ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.NAME));
            String address = cursor.getString(
                    cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.ADDRESS));
            String phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.PHONE));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.DESCRIPTION));
            String tag = cursor.getString(
                    cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.TAG));
            float rating = cursor.getLong(cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.RATING));
            double latitude = cursor.getLong(cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.LATITUDE));
            double longitude = cursor.getLong(cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.LONGITUDE));

            Restaurant restaurant = new Restaurant(id, name, address, phone, description, tag, rating, latitude, longitude);
            return restaurant;
        }

        cursor.close();
        return null;
    }
    public boolean updateRestaurant(SQLiteDatabase db, long restaurantId, String name, String address, String phone, String description, String tag,  float rating, double latitude, double longitude){
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.NAME, name);
        values.put(RestaurantContract.RestaurantEntry.ADDRESS, address);
        values.put(RestaurantContract.RestaurantEntry.PHONE, phone);
        values.put(RestaurantContract.RestaurantEntry.DESCRIPTION, description);
        values.put(RestaurantContract.RestaurantEntry.TAG, tag);
        values.put(RestaurantContract.RestaurantEntry.RATING, rating);
        values.put(RestaurantContract.RestaurantEntry.LATITUDE, latitude);
        values.put(RestaurantContract.RestaurantEntry.LONGITUDE, longitude);

        db.update(RestaurantContract.RestaurantEntry.TABLE_NAME, values, "ID= ?", new String[]{Long.toString(restaurantId)});
        return true;
    }
    public boolean removeRestaurant(SQLiteDatabase db, Restaurant restaurant){

        db.delete(RestaurantContract.RestaurantEntry.TABLE_NAME,"ID= ?", new String[]{Long.toString(restaurant.getId())});
        return true;
    }

    public Cursor getAllRestaurants(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM " + RestaurantContract.RestaurantEntry.TABLE_NAME, null);
        return c;
    }
}
