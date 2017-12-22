package com.davey.davescogsworth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    // used for location services
    public FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checks to see if the user gives permission for the app to use its location.
        //if they have not yet given permission it will prompt the user here
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Setup FAB to open AlarmDetailsActivity
        FloatingActionButton addAlarmFab =  findViewById(R.id.add_alarm_fab);

        // Setup FAB to open Alarm List
        FloatingActionButton alarmListFab =  findViewById(R.id.alarm_list_fab);

        addAlarmFab.setOnClickListener(new View.OnClickListener() {
            //@TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AlarmDetailsActivity.class);
                // Set the URI on the data field of the intent
                //intent.setData(currentAlarmUri);
                intent.putExtra("NameOfCallingClass", MainActivity.this.toString());
                startActivity(intent);
            }
        });

        alarmListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmListActivity.class);

                // Set the URI on the data field of the intent
                //intent.setData(currentAlarmUri);
                intent.putExtra("NameOfCallingClass", MainActivity.this.toString());

                startActivity(intent);
            }
        });
    }

    /**
     * THIS CHECKS PERMISSIONS FOR THE LOCATION
     *
     * if app does not have permission it asks the user to provide it
     */
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }
}