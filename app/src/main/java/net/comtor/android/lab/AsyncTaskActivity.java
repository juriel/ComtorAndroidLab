package net.comtor.android.lab;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncTaskActivity extends AppCompatActivity {
    private TextView contador;
    private Handler updateTextHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ExecutorService executor = Executors.newFixedThreadPool(2);
                executor.execute(new FutureTask<String>(new Contar()));
            }
        });
        contador = findViewById(R.id.contador);

        updateTextHandler = new UpdateTextHandler();


    }

    class UpdateTextHandler extends  Handler{
        UpdateTextHandler(){
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = msg.getData().getString("message");
            contador.setText(str);
        }
    }
    class Contar implements Callable<String> {
        int i ;
        @Override
        public String call() throws Exception {
            for( i = 0; i < 1000000; i++){
                Message msg = new Message();
                msg.getData().putString("message","AVISO "+i);
                updateTextHandler.sendMessage(msg);
                Thread.sleep(500);
            }
            return "Termino";
        }
    }
}