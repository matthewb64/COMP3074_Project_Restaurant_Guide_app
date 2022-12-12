package com.restaurantguide;

import android.provider.BaseColumns;

public class RestaurantContract {
    private RestaurantContract(){}

    public static class RestaurantEntry implements BaseColumns {

        public static final String TABLE_NAME = "restaurant";
        public static final String _ID = "ID";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String PHONE = "phone";
        public static final String DESCRIPTION = "description";
        public static final String TAG = "tag";
        public static final String RATING = "rating";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    public static final String SQL_CREATE_RESTAURANT =
            "CREATE TABLE " + RestaurantEntry.TABLE_NAME+" ("+
                    RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    RestaurantEntry.NAME + " TEXT, " +
                    RestaurantEntry.ADDRESS + " TEXT, " +
                    RestaurantEntry.PHONE + " TEXT, " +
                    RestaurantEntry.DESCRIPTION + " TEXT, " +
                    RestaurantEntry.TAG + " TEXT, " +
                    RestaurantEntry.RATING + " NUMBER, " +
                    RestaurantEntry.LATITUDE + " DECIMAL(9, 12), " +
                    RestaurantEntry.LONGITUDE + " DECIMAL(9, 12))";

    public static final String SQL_DROP_RESTAURANT =
            "DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME;
}

