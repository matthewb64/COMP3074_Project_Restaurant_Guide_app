package com.restaurantguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAbout, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Defining Button
        btnAbout = findViewById(R.id.btnAbout);
        btnList = findViewById(R.id.btnList);

        // Set Click Listener
        btnAbout.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.btnAbout:
                i = new Intent(this, AboutUsActivity.class);
                startActivity(i);
                break;
            case R.id.btnList:
                i = new Intent(this, RestaurantActivity.class);
                startActivity(i);
                break;
        }

    }
}
