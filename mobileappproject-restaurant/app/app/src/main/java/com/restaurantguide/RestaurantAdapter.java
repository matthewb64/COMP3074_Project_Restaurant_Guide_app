package com.restaurantguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private ArrayList<Restaurant> dataSet;
    List<Restaurant> restaurantList;
    Context context;

    public RestaurantAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> objects) {
        super(context, resource, objects);
        this.context = context;
        this.restaurantList = objects;
        this.dataSet = new ArrayList<Restaurant>();
        this.dataSet.addAll(restaurantList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inf = LayoutInflater.from(context);
            convertView = inf.inflate(R.layout.custom_layout, parent, false);
        }

        TextView name = convertView.findViewById(R.id.txtName);
        TextView tag = convertView.findViewById(R.id.txtTag);



        Restaurant r = restaurantList.get(position);
        name.setText(r.getName());
        tag.setText(r.getTag());

        return  convertView;
    }

    // filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        restaurantList.clear();
        if (charText.length() == 0) {
            restaurantList.addAll(dataSet);
        } else {
            for (Restaurant restaurant: dataSet) {
                // Search by Name
                if (restaurant.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    restaurantList.add(restaurant);
                }
                // Search by Tag
                if (restaurant.getTag().toLowerCase(Locale.getDefault()).contains(charText)) {
                    restaurantList.add(restaurant);
                }
            }
        }
        notifyDataSetChanged();
    }
}
