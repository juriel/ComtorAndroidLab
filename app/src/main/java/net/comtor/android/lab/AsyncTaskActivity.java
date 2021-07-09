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
                Thread th = new ContarHilo();
                th.start();

                //ExecutorService executor = Executors.newFixedThreadPool(2);
                //executor.execute(new FutureTask<String>(new Contar()));
                fab.setEnabled(false);
            }
        });
        contador = findViewById(R.id.contador);
        if(savedInstanceState != null){
            contador.setText(savedInstanceState.getString("AVISO"));
        }
    }



    class Contar implements Callable<String> {
        int i ;
        @Override
        public String call() throws Exception {
            for( i = 0; i < 10; i++){
                runOnUiThread(() -> {contador.setText("Contando "+i);});

                Thread.sleep(500);
                /*
                Message msg = new Message();
                msg.getData().putString("message","AVISO "+i);
                updateTextHandler.sendMessage(msg);
                Thread.sleep(500);

                 */
            }


            runOnUiThread(()-> {fab.setEnabled(true);});


            fab.setEnabled(false);

            return "Termino";
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("AVISO",contador.getText().toString());
    }


    class ContarHilo extends Thread{
        int i ;
        @Override
        public void run() {
            haga();
        }
    }

    public void haga(){
        for( int i = 0; i < 1000000; i++) {
            System.out.println(" C "+i);
            ii = i;
            runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            contador.setText("Contando " + ii);
                        }
                    }
            );

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}