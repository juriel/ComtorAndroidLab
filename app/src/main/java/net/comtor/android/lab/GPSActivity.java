package net.comtor.android.lab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.comtor.helper.PermissionHelper;

public class GPSActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_01";

    Intent serviceIntent = null;
    private GPSService mService = null;
    private boolean mBound = false;


    private TextView message;
    private Button requestPermissions;
    private Button btn_start_service;

    private ActivityResultLauncher<String> requestPermissionLauncher =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                    message.setText("Gracias perrito " + PermissionHelper.currentPermissionsStr(this));
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                    message.setText("No sea chanda " + PermissionHelper.currentPermissionsStr(this));
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        requestPermissions = findViewById(R.id.btn_request_permission);
        requestPermissions.setOnClickListener(v -> {onClickRequestPermission(v);});

        btn_start_service = findViewById(R.id.btn_start_gps);
        btn_start_service.setOnClickListener(v-> {onClickStartService(v);});

        message = findViewById(R.id.gps_message);
        message.setText(PermissionHelper.currentPermissionsStr(this));
        if (PermissionHelper.getFirstNonGrantedPermission(this) == null){
            startGpsService();
        }
    }

    @Override
    protected void onStop() {
        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
        super.onStop();
        unbindService(connection);
    }

    private void startGpsService(){
        serviceIntent = new Intent(this, GPSService.class);
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    private void onClickStartService(View v) {
        startGpsService();
    }

    public void onClickRequestPermission(View view) {
        String p = PermissionHelper.getFirstNonGrantedPermission(this);
        if (p != null) {
            requestPermissionLauncher.launch(p);
        }
    }


    /** Defines callbacks for service binding, passed to bindService() */

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            GPSService.LocalBinder binder = (GPSService.LocalBinder) service;
            mService = binder.getService();
            mService.startGpsProvider();
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };


}