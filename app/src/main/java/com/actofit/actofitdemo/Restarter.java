package com.actofit.actofitdemo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.IOException;


public class Restarter extends BroadcastReceiver {

    public static final String COMMAND = "SENDER";
    public static final int SENDER_ACT_DOCUMENT = 0;
    public static final int SENDER_SRV_POSITIONING = 1;
    public static final int MIN_TIME_REQUEST = 5 * 1000;
    public static final String ACTION_REFRESH_SCHEDULE_ALARM =
            "org.mabna.order.ACTION_REFRESH_SCHEDULE_ALARM";
    private static Location currentLocation;
    private static Location prevLocation;
    private static Context context;
    private String provider = LocationManager.GPS_PROVIDER;
    private static Intent _intent;
    private static LocationManager locationManager;
    private static String filename = "demoFile.txt";

    private static LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            try {
                gotLocation(location);
            } catch (Exception e) {
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        context = context;
        _intent = intent;
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider,
                    MIN_TIME_REQUEST, 5, locationListener);
            Location gotLoc = locationManager
                    .getLastKnownLocation(provider);
            gotLocation(gotLoc);
        }
    }

    private static void gotLocation(Location location) {
        prevLocation = currentLocation == null ? null : new Location(currentLocation);
        currentLocation = location;
        if(currentLocation != null)
        {
            writeData(location);
        }

    }

    private static void writeData(Location currentLocation) {

        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            String data = "" + currentLocation.getLatitude() + "," + currentLocation.getLongitude();
            fos.write(data.getBytes());
            fos.flush();
            //fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("writing to file", filename + "completed...");
    }
}