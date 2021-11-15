package com.actofit.actofitdemo;

import android.annotation.SuppressLint;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.io.FileOutputStream;
import java.io.IOException;

public class LocationUpdateService extends JobIntentService {

    //region data
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;

    private String filename = "demoFile.txt";


    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }


    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location currentLocation = locationResult.getLastLocation();
            Log.d("Locations", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
            //Share/Publish Location

            sendDataToActivity(currentLocation);

        }
    };

    private void sendDataToActivity(Location currentLocation)
    {
        Intent sendLevel = new Intent();
        sendLevel.setAction("GPSLocationUpdates");
        sendLevel.putExtra( "LOCATION_DATA",""+currentLocation.getLatitude() + "," + currentLocation.getLongitude());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(sendLevel);

        writeData(currentLocation);


    }

    private void writeData(Location currentLocation) {

        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String data = ""+currentLocation.getLatitude() + "," + currentLocation.getLongitude();
            fos.write(data.getBytes());
            fos.flush();
            //fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("writing to file", filename + "completed...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startLocationUpdates();

        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback, Looper.myLooper());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initData() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        initData();
    }
}