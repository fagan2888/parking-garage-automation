package com.glleung.parkinggarageautomation;

/**
 * Created by Aorsini on 6/28/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReservationListAdapter extends ArrayAdapter<Vehicle> {
    public ReservationListAdapter(Context context, ArrayList<Vehicle> vehicles) { //users??
        super(context, 0, vehicles);
    }

    private static class ViewHolder {
        TextView makemodel;
        TextView year;
        TextView licenseplate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Vehicle vehicle = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vehicle_item, parent, false);
            viewholder.makemodel = (TextView) convertView.findViewById(R.id.make_model_item);
            viewholder.year = (TextView) convertView.findViewById(R.id.vehicle_year_item);
            viewholder.licenseplate = (TextView) convertView.findViewById(R.id.licenseplate_item);
            viewholder.makemodel.setText(vehicle.make + " " + vehicle.model);
            viewholder.year.setText(vehicle.year);
            viewholder.licenseplate.setText("License Plate: " + vehicle.licenseplate);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.makemodel = (TextView) convertView.findViewById(R.id.make_model_item);
        viewholder.year = (TextView) convertView.findViewById(R.id.vehicle_year_item);
        viewholder.licenseplate = (TextView) convertView.findViewById(R.id.licenseplate_item);
        // Return the completed view to render on screen
        return convertView;
    }
}