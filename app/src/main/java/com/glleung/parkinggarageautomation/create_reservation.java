package com.glleung.parkinggarageautomation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class create_reservation extends ActionBarActivity {

    //if (currentUser())
    EditText entryTime;
    EditText exitTime;
    Button nextButton;
    RadioGroup rg;

    //garage admin ref
    Firebase myFirebaseRef;
    private String paymentSelected = "Credit Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        nextButton = (Button) findViewById(R.id.next);
       // entryTime = (EditText) findViewById(R.id.entry_time);
       // exitTime = (EditText) findViewById(R.id.exit_time);
        rg = (RadioGroup) findViewById(R.id.payment_options);

        myFirebaseRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentEmail = "";//email.getText().toString();
                myFirebaseRef.createUser(
                        currentEmail,
                        "defaultpass",
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                // user was created
                                String uid = (String) result.get("uid");
                                Toast.makeText(getApplicationContext(), "Pass - " + uid, Toast.LENGTH_LONG).show();

                                Firebase adminRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/" + uid);
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("entryTime", entryTime.getText().toString());
                                map.put("exitTime", exitTime.getText().toString());
                                map.put("paymentform", paymentSelected);
                    //            map.put("userid",currentuser().userid.getText().toString());
                     //           map.put("email", currentuser().email.getText().toString());
                    //            map.put("phonenumber", currentuser().phone.getText().toString());
                                adminRef.setValue(map);
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                // there was an error]
                                Toast.makeText(getApplicationContext(), firebaseError.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}