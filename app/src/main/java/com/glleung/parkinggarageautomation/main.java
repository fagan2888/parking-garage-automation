package com.glleung.parkinggarageautomation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class main extends ActionBarActivity {

    final int VENMO_REQUEST = 10;  // The request code
    final String appId = "3417";
    final String appName = "Parking Automation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button new_reservation = (Button)findViewById(R.id.create_reservations);
        new_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main.this, create_reservation.class));
            }
        });

        Button new_vehicle = (Button)findViewById(R.id.register_vehicles);
        new_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main.this, register_vehicle.class));
            }
        });

        Button payment_button = (Button) findViewById(R.id.make_payment);
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!VenmoLibrary.isVenmoInstalled(main.this)){
                    Toast.makeText(main.this,"Venmo is not installed",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(main.this,"Venmo is installed",Toast.LENGTH_LONG).show();
                    Intent venmoIntent = VenmoLibrary.openVenmoPayment(appId, appName, "9084006666", "0.01", "Parking", "pay");
                    startActivityForResult(venmoIntent, VENMO_REQUEST);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
