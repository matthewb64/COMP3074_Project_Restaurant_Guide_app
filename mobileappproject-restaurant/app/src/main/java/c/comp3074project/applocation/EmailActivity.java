package c.comp3074project.applocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static c.comp3074project.applocation.Global.latitude;
import static c.comp3074project.applocation.Global.location;
import static c.comp3074project.applocation.Global.longitude;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        TextView txtAddress = findViewById(R.id.txtAddressResult);
        TextView txtLat = findViewById(R.id.txtLatResult);
        TextView txtLng = findViewById(R.id.txtLngResult);


        txtAddress.setText(location);
        txtLat.setText(latitude);
        txtLng.setText(longitude);

    }

    public void onButtonTap(View v){

        EditText mail = findViewById(R.id.txtAddress);
        String inputMail = mail.getText().toString();
        EditText subject = findViewById(R.id.txtSubject);
        String inputSubject = subject.getText().toString();
        EditText message = findViewById(R.id.txtContent);
        String inputMessage = message.getText().toString();

        if(!inputMail.equals("") || !inputSubject.equals("") || !inputMessage.equals("")){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {inputMail});
            intent.putExtra(Intent.EXTRA_SUBJECT, inputSubject);
            intent.putExtra(Intent.EXTRA_TEXT, inputMessage);
            startActivity(Intent.createChooser(intent, inputSubject));
        }
    }
}
