package net.comtor.android.lab;

import android.Manifest;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class GPSService extends Service {
    public final static int GPS_SERVICE_NOTIFICATION_ID = 1213131;

    private final IBinder binder = new LocalBinder();
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback = null;

    public GPSService() {
        Log.d("COMTOR_LAB", " GPSService constructor");
        SendLogMessageThread.log("GPSService constructor");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int resp = super.onStartCommand(intent, flags, startId);
        startGpsProvider();
        SendLogMessageThread.log("onStartCommand");
        return resp;
    }


    @Override
    public void onDestroy() {
        SendLogMessageThread.log("onDestroy");
        JobInfo.Builder jb = new JobInfo.Builder(ComtorGpsJobService.JOB_ID,new ComponentName(this,ComtorGpsJobService.class));
        jb.setPersisted(true);
        jb.setPeriodic(30000);

        jb.setRequiresDeviceIdle(false);
        jb.setRequiresCharging(false);
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int r = scheduler.schedule(jb.build());
        if (r<=0){
            SendLogMessageThread.log("Fallo scheduler");
        }
        else {
            SendLogMessageThread.log("Scheduler OK "+r);

        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("COMTOR_LAB", " bind");
        SendLogMessageThread.log("bind");

        return binder;
    }


    public void startGpsProvider() {
        SendLogMessageThread.log("startGpsProvider");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            if (locationCallback == null) {
                locationCallback = new ComtorGPSLocationCallback();
            }
            if (fusedLocationClient == null) {
                fusedLocationClient = ComtorGPSUtils.createFusedLocationProviderClient(this, locationCallback,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            SendLogMessageThread.log("GPSSERVICE "+e.getMessage());

        }

    }


    public class LocalBinder extends Binder {
        GPSService getService() {
            return GPSService.this;
        }
    }

}