package com.glleung.parkinggarageautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceEstimate extends AppCompatActivity {

    TextView priceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_estimate);

        priceValue = (TextView) findViewById(R.id.calculated_price_value);
        Bundle b = getIntent().getExtras();
        int hour = b.getInt("hour1");
        int month = b.getInt("month1");
        int year = b.getInt("year1");
        int day = b.getInt("day1");

        int hour2 = b.getInt("hour2");
        int month2 = b.getInt("month2");
        int year2 = b.getInt("year2");
        int day2 = b.getInt("day2");

        double cost = calculatePrice(year,month,day,hour,year2,month2,day2,hour2);
        displayPrice(cost);
    }

    private void displayPrice(double price){
        price = round(price,2);
        priceValue.setText("$ " + price);
    }

    //TODO fix edge cases for changing month, day, and hour
    private double calculatePrice(int year,int month, int day, int hour,int year2,int month2, int day2, int hour2){
        double result = 0.0;
        int temp = month2 - month;
        if(temp>0) {
            result += (temp - 1) * 99.50;
        }

        temp = day2-day;
        if(temp>0){
            result += (temp - 1) * 4.90;
        }

        temp = hour2 - hour;
        if(temp>0){
            result += temp*.90;
        }

        return result;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
