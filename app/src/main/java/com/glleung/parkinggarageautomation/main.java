package com.glleung.parkinggarageautomation;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class main extends ActionBarActivity {

    final int VENMO_REQUEST = 10;  // The request code
    final String appId = "3417";
    final String appName = "Parking Automation";
    final String appSecret = "DFYGuUXtsZWQFrveYPD8gNnH9jKT46Zy";

    private static double latestBalance;
    public static ArrayList<Vehicle> currentVehicles;
    public static List<Long> vehicleIds;

    TextView balanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceView = (TextView) findViewById(R.id.balance_view);

        final Firebase firebaseRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/" + MainLogin.currentUID);
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    double balance = (double) dataSnapshot.child("balance").getValue();
                    main.latestBalance = balance;
                    balanceView.setText("Balance: $" + balance);
                }
                catch (Exception e){
                    //no balance will display
                    double balance = 0.00;
                    main.latestBalance = balance;
                    balanceView.setText("Balance: $" + balance);
                }

                String name = (String) dataSnapshot.child("firstname").getValue();
                TextView welcomeView = (TextView) findViewById(R.id.main_welcome);
                welcomeView.setText("Welcome, " + name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Button new_reservation = (Button) findViewById(R.id.create_reservations);
        new_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main.this, create_reservation_time.class));
            }
        });

        Button new_vehicle = (Button) findViewById(R.id.register_vehicles);
        new_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main.this, register_vehicle.class));
            }
        });

        Button vehicle_list = (Button) findViewById(R.id.view_vehicles);
        vehicle_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main.this,RegisteredVehicles.class));
            }
        });

        Button payment_button = (Button) findViewById(R.id.make_payment);
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VenmoLibrary.isVenmoInstalled(main.this)) {
                    Toast.makeText(main.this, "Venmo is not installed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(main.this, "Venmo is installed", Toast.LENGTH_LONG).show();
                    double owed = .01;
                    if (main.latestBalance < 0) {
                        owed = -1 * latestBalance;
                    }
                    String amount = Double.toString(owed);
                    Intent venmoIntent = VenmoLibrary.openVenmoPayment(appId, appName, "9084006666", amount, "Parking", "pay");
                    startActivityForResult(venmoIntent, VENMO_REQUEST);
                }
            }
        });

        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Long> vehicleids = (HashMap<String, Long>) dataSnapshot.child("vehicles").getValue();
                main.vehicleIds = new ArrayList<Long>();
                for(long id : vehicleids.values()){
                    main.vehicleIds.add(id);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase vehicleRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/vehicles/");
        vehicleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                main.currentVehicles = new ArrayList<Vehicle>();
                for(long id:main.vehicleIds){
                    HashMap<String,String> curr = (HashMap<String,String>) dataSnapshot.child(""+id).getValue();
                    String make = curr.get("make");
                    String model = curr.get("model");
                    String year = curr.get("year");
                    String uid = curr.get("user");
                    String licenseplate = curr.get("licenseplate");
                    String color = curr.get("color");
                    Vehicle temp = new Vehicle(make,model,color,year,licenseplate,uid);
                    main.currentVehicles.add(temp);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case VENMO_REQUEST: {
                if (resultCode == RESULT_OK) {
                    String signedrequest = data.getStringExtra("signedrequest");
                    if (signedrequest != null) {
                        VenmoLibrary.VenmoResponse response = (new VenmoLibrary()).validateVenmoPaymentResponse(signedrequest, appSecret);
                        if (response.getSuccess().equals("1")) {
                            //Payment successful.  Use data from response object to display a success message
                            String note = response.getNote();
                            String amount = response.getAmount();
                            //Toast.makeText(this,amount,Toast.LENGTH_LONG).show();

                            double val = Double.parseDouble(amount);

                            Firebase firebaseRef = new Firebase("https://incandescent-fire-3535.firebaseio.com/users/" + MainLogin.currentUID);
                            val = main.latestBalance + val;
                            val = round(val,2);
                            main.latestBalance = val;
                            balanceView.setText("Balance: $" + val);
                            firebaseRef.child("balance").setValue(val);
                        }
                    } else {
                        String error_message = data.getStringExtra("error_message");
                        //An error ocurred.  Make sure to display the error_message to the user
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    //The user cancelled the payment
                }
                break;
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
