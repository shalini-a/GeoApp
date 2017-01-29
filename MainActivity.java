package com.example.shagniho.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static TextView temperatureTextView;
    static TextView placeTextView;
    private Button btnSendLocation, btnAddContact;
    private RelativeLayout addContactLayout;
    Location loc;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureTextView = (TextView)findViewById(R.id.tempTextView);
        placeTextView = (TextView)findViewById(R.id.locationTextView);
        btnSendLocation = (Button)findViewById(R.id.send_location);
        btnAddContact = (Button) findViewById(R.id.add_contact);
        addContactLayout = (RelativeLayout) findViewById(R.id.add_contact_layout);

        DownloadTask downloadTask = new DownloadTask();
        GPSTracker gps = new GPSTracker(MainActivity.this);
        loc = gps.getLocation();
        Log.d("shalini", "loc "+loc);
        if(loc != null) {
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&appid=9e99409ab081ba6adb9af6cfe4a02bf7");
            btnSendLocation.setOnClickListener(this);
            btnAddContact.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        switch (id) {
            case R.id.send_location:
             //   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q="+latitude+","+longitude));
             //   startActivity(intent);
            Intent intent = new Intent(MainActivity.this , SmsActivity.class);

                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(
                            loc.getLatitude(),
                            loc.getLongitude(),
                            // In this sample, get just a single address.
                            1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //String location = "https://www.google.co.in/maps/@"+latitude+","+longitude;
            intent.putExtra("Location" , "https://maps.google.com/?q="+latitude+","+longitude);
            startActivity(intent);
            break;
            case R.id.add_contact:
            addContactLayout.setVisibility(View.VISIBLE);
            break;
        }
    }
}
