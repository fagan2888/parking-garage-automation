package com.glleung.parkinggarageautomation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class register_vehicle extends ActionBarActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        submit = (Button) findViewById(R.id.vehicle_next);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebaseCount = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
                firebaseCount.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = (long) dataSnapshot.getValue();
                        Toast.makeText(register_vehicle.this,"count is " + count, Toast.LENGTH_LONG).show();
                        count++;
                        Firebase ref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/"+count);

                        EditText makeText = (EditText) findViewById(R.id.vehicle_make);
                        EditText modelText = (EditText) findViewById(R.id.vehicle_model);
                        EditText yearText = (EditText) findViewById(R.id.vehicle_year);
                        EditText licenseplateText = (EditText) findViewById(R.id.vehicle_license);

                        Map<String,String> map = new HashMap<String, String>();
                        map.put("color","n/a");
                        map.put("licenseplate",licenseplateText.getText().toString());
                        map.put("make",makeText.getText().toString());
                        map.put("model",modelText.getText().toString());
                        map.put("year",yearText.getText().toString());
                        map.put("user",MainLogin.currentUID);
                        ref.setValue(map);

                        Firebase countref = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
                        countref.child("count").setValue(count);

                        Firebase userref = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/" + MainLogin.currentUID);
                        userref.child("vehicles").push().setValue(count);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_vehicle, menu);
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