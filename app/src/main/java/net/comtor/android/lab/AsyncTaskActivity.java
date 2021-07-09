package net.comtor.android.lab;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncTaskActivity extends AppCompatActivity {
    private TextView counterLbl;
    FloatingActionButton fab;
    int ii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ScheduledExecutorService backgroundExecutor = Executors.newSingleThreadScheduledExecutor();
                //Schedule Thread. It allows UI thread to update
                backgroundExecutor.schedule(new CounterThread(),1, TimeUnit.SECONDS);

                fab.setEnabled(false);
            }
        });
        counterLbl = findViewById(R.id.contador);
        if(savedInstanceState != null){
            counterLbl.setText(savedInstanceState.getString("AVISO"));
        }
    }


    class CounterThread implements Runnable{
        int i ;
        @Override
        public void run() {
            for( int i = 0; i < 100; i++) {
                System.out.println(" Counting "+i);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                counterLbl.setText("Contando " + ii);
                            }
                        }
                );
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(() -> {counterLbl.setText("Finished");});
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("AVISO", counterLbl.getText().toString());
    }
}