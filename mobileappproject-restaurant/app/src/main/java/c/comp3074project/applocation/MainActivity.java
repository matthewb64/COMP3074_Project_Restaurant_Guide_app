package c.comp3074project.applocation;

import android.app.Application;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int MY_TAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLoc = findViewById(R.id.btnLoc);
        Button btnMail = findViewById(R.id.btnMail);
        btnLoc.setOnClickListener(this);
    }


    //Calls Map activity, setting global variables for testing use
    @Override
    public void onClick(View v) {

        EditText loc = findViewById(R.id.txtLoc);
        String inputLoc = loc.getText().toString();
        EditText lat = findViewById(R.id.txtLat);
        String inputLat = lat.getText().toString();
        EditText lon = findViewById(R.id.txtLon);
        String inputLon = lon.getText().toString();

        if(!loc.equals("")){
            Global.location = inputLoc;
            Global.latitude = inputLat;
            Global.longitude = inputLon;
            Intent i = new Intent(v.getContext(), MapsActivity.class);
            startActivityForResult(i, MY_TAG);
        }
    }

    //Calls email activity.
    public void onTap(View v){
        EditText loc = findViewById(R.id.txtLoc);
        String inputLoc = loc.getText().toString();
        EditText lat = findViewById(R.id.txtLat);
        String inputLat = lat.getText().toString();
        EditText lon = findViewById(R.id.txtLon);
        String inputLon = lon.getText().toString();

        if(!loc.equals("")){
            Global.location = inputLoc;
            Global.latitude = inputLat;
            Global.longitude = inputLon;
            Intent i = new Intent(v.getContext(), EmailActivity.class);
            startActivityForResult(i, MY_TAG);
        }
    }
}
