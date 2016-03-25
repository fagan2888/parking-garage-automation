package com.glleung.parkinggarageautomation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class RegisteredVehicles extends ActionBarActivity {

    ListView vehicleListView;
    VehicleListAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_vehicles);

        vehicleListView = (ListView) findViewById(R.id.vehicle_listview);

        vehicleAdapter = new VehicleListAdapter(this,main.currentVehicles);
        vehicleListView.setAdapter(vehicleAdapter);

    }

}
