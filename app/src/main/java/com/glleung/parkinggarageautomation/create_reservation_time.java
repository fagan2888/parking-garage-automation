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
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class create_reservation_time extends ActionBarActivity implements DatePickerDialog.OnDateSetListener{

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Button select_checkin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation_time);

        dateView = (TextView) findViewById(R.id.check_in_date);
        select_checkin_button = (Button) findViewById(R.id.checkin_date_button);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String s = formatter.format(calendar.getTime());

        dateView.setText(s);

        select_checkin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, year);
        calendar2.set(Calendar.MONTH, monthOfYear);
        calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String s = formatter.format(calendar2.getTime());

       dateView.setText(s);
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
