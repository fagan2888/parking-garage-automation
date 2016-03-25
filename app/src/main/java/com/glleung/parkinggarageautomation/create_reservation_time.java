package com.glleung.parkinggarageautomation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class create_reservation_time extends ActionBarActivity implements DatePickerDialog.OnDateSetListener{

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateInView;
    private TextView timeInView;
    private TextView dateOutView;
    private TextView timeOutView;
    private int year, month, day,hour,minute;
    private int year2, month2, day2, hour2, minute2;
    Button select_checkindate_button;
    Button select_checkintime_button;
    Button select_checkoutdate_button;
    Button select_checkouttime_button;
    boolean inClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation_time);

        dateInView = (TextView) findViewById(R.id.check_in_date);
        timeInView = (TextView) findViewById(R.id.check_in_time);
        dateOutView = (TextView) findViewById(R.id.check_out_date);
        timeOutView = (TextView) findViewById(R.id.check_out_time);
        select_checkindate_button = (Button) findViewById(R.id.checkin_date_button);
        select_checkintime_button = (Button) findViewById(R.id.checkin_time_button);
        select_checkoutdate_button = (Button) findViewById(R.id.checkout_date_button);
        select_checkouttime_button = (Button) findViewById(R.id.checkin_time_button);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);


        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String s = formatter.format(calendar.getTime());

        dateInView.setText("Check-in Date: " + s);
        timeInView.setText("Check-in Time: " + hour + ":" + minute);

        dateOutView.setText("Check-out Date: " + s);
        timeOutView.setText("Check-out Time: " + hour + ":" + minute);


        select_checkindate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inClicked = true;
                Bundle b = new Bundle();
                b.putInt(DatePickerDialogFragment.YEAR, year);
                b.putInt(DatePickerDialogFragment.MONTH, month);
                b.putInt(DatePickerDialogFragment.DATE, day);
                DialogFragment picker = new DatePickerDialogFragment();
                picker.setArguments(b);
                picker.show(getFragmentManager(), "fragment_date_picker");
            }
        });

        select_checkoutdate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inClicked = false;
                Bundle b = new Bundle();
                b.putInt(DatePickerDialogFragment.YEAR, year);
                b.putInt(DatePickerDialogFragment.MONTH, month);
                b.putInt(DatePickerDialogFragment.DATE, day);
                DialogFragment picker = new DatePickerDialogFragment();
                picker.setArguments(b);
                picker.show(getFragmentManager(), "fragment_date_picker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
    {
        if(inClicked) {
            this.year = year;
            this.month = monthOfYear;
            this.day = dayOfMonth;

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.YEAR, year);
            calendar2.set(Calendar.MONTH, monthOfYear);
            calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(calendar2.getTime());

            dateInView.setText("Check-in Date: " + s);
        }
        else{
            this.year2 = year;
            this.month2 = monthOfYear;
            this.day2 = dayOfMonth;

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.YEAR, year);
            calendar2.set(Calendar.MONTH, monthOfYear);
            calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            String s = formatter.format(calendar2.getTime());

            dateOutView.setText("Check-out Date: " + s);
        }
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
