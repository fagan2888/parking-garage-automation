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


public class new_user extends ActionBarActivity {

    EditText firstName;
    EditText lastName;
    EditText phone;
    EditText email;
    Button nextButton;
    RadioGroup rg;
    Firebase myFirebaseRef;
    private String paymentSelected = "Credit Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        nextButton = (Button) findViewById(R.id.next);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        rg = (RadioGroup) findViewById(R.id.payment_options);

        myFirebaseRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentEmail = email.getText().toString().toLowerCase();
                myFirebaseRef.createUser(
                        currentEmail,
                        "defaultpass",
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                // user was created
                                String uid = (String) result.get("uid");
                                Toast.makeText(getApplicationContext(), "Pass - "+ uid, Toast.LENGTH_LONG).show();
                                double bal = 0.00;

                                Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/"+uid);
                                Map<String,Object> map = new HashMap<String, Object>();
                                map.put("email",currentEmail);
                                map.put("firstname",firstName.getText().toString());
                                map.put("lastname",lastName.getText().toString());
                                map.put("paymentform",paymentSelected);
                                map.put("phonenumber",phone.getText().toString());
                                map.put("balance",bal);
                                ref.setValue(map);
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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case 0:
                        paymentSelected = "Credit Card";
                        break;
                    case 1:
                        paymentSelected = "Venmo";
                        break;
                    case 2:
                        paymentSelected = "Paypal";
                        break;
                    default:
                        break;
                }
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
