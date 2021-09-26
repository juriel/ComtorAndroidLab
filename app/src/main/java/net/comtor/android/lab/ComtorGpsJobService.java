package net.comtor.android.lab;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import androidx.core.app.ActivityCompat;

public class ComtorGpsJobService extends JobService {
    final static public int JOB_ID = 2121212121;
    private FusedLocationProviderClient fusedLocationClient = null;
    private LocationCallback locationCallback = null;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        SendLogMessageThread.log("onStartJob SCHED");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            SendLogMessageThread.log("onStartJob No permissions");

            return false;
        }
        try {
            if (locationCallback == null) {
                locationCallback = new ComtorGPSLocationCallback();
            }
            if (fusedLocationClient == null) {
                Worker w = new Worker();
                w.start();

                fusedLocationClient = ComtorGPSUtils.createFusedLocationProviderClient(this, locationCallback,w.getLooper());

            }
        } catch (Exception e) {
            e.printStackTrace();
            SendLogMessageThread.log("ERROR " + e.getMessage());
        }
        SendLogMessageThread.log("TODO OK");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        SendLogMessageThread.log("onStopJob SCHED");
        return false;
    }

    class Worker extends HandlerThread {

        public Worker() {
            super("Worker");
        }

    }
}
