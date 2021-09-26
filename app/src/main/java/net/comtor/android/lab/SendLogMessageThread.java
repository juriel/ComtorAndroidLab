package net.comtor.android.lab;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendLogMessageThread extends Thread{
    private final String message;

    SendLogMessageThread(String message){
        this.message = message;
    }
    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            String url = "https://games.comtor.net/gps/?message=" + URLEncoder.encode(message, "UTF-8");
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                Log.d("COMTOR_LAB", response.toString());


            } catch (IOException ex) {
                ex.printStackTrace();
                Log.d("COMTOR_LAB", ex.getMessage());
            }
        }
        catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }
    public static void log(String message){
        Log.d("ComtorLab",message);
        Thread th = new SendLogMessageThread(message);
        th.start();
    }
}
