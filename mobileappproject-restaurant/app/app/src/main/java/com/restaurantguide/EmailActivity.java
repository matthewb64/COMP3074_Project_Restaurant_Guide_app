package com.restaurantguide;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    // Define DB
    private SQLiteDatabase db;
    private DBHelper myDB;

    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // DB instantiation
        myDB = new DBHelper(this);
        db = myDB.getWritableDatabase();

        // Get restaurantID from intent
        Intent i = getIntent();
        final long restaurantID = i.getLongExtra("restaurantID", -1);
        Restaurant r = myDB.getRestaurant(db, restaurantID);

        EditText subject = findViewById(R.id.edtSubject);
        subject.setText(r.getName());
        EditText message = findViewById(R.id.edtMessage);
        message.setText(r.getDescription());

        //Defining Button
        btnSend = findViewById(R.id.btnSend);
        // set event for btnEdit
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText mail = findViewById(R.id.edtEmail);
                String inputMail = mail.getText().toString();
                EditText subject = findViewById(R.id.edtSubject);
                String inputSubject = subject.getText().toString();
                EditText message = findViewById(R.id.edtMessage);
                String inputMessage = message.getText().toString();

                if(!inputMail.equals("") || !inputSubject.equals("") || !inputMessage.equals("")){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    //Sets field values for sent email
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {inputMail});
                    intent.putExtra(Intent.EXTRA_SUBJECT, inputSubject);
                    intent.putExtra(Intent.EXTRA_TEXT, inputMessage);
                    startActivity(Intent.createChooser(intent, inputSubject));
                }
            }
        });
    }
}
