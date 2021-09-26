package net.comtor.android.lab;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class ComtorGPSUtils {


    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static FusedLocationProviderClient createFusedLocationProviderClient(Context ctx, LocationCallback locationCallback,Looper looper) throws Exception {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (looper == null){
            looper = Looper.getMainLooper();
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, looper);
        return fusedLocationClient;
    }

}
