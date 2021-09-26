package net.comtor.android.lab;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ComtorGPSLocationCallback extends LocationCallback {
    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        for (Location location : locationResult.getLocations()) {
            SaveLocationThread th= new SaveLocationThread(location);
            th.start();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        SendLogMessageThread.log("ComtorGPSLocationCallback finalize");
        super.finalize();
    }

    private class SaveLocationThread extends  Thread{
        Location location;
        SaveLocationThread(Location location){
            this.location = location;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            String url = "https://games.comtor.net/gps/?lat=" + location.getLatitude() + "&lon=" + location.getLongitude();
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                Log.d("COMTOR_LAB", response.toString());


            } catch (IOException ex) {
                ex.printStackTrace();

                Log.d("COMTOR_LAB", ex.getMessage());
            }
        }
    }
}
