package net.comtor.android.lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Aqui se creo la pantalla a partir del XML


        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        label = findViewById(R.id.lbl_Hello);
        //Metodo 1
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        label.setText("Boton 1");
                        Intent intent = new Intent(MainActivity.this,AsyncTaskActivity.class);
                        startActivity(intent);
                    }
                }
        );
        //Metodo 2

        btn2.setOnClickListener((View  v) -> {
            label.setText("Boton 2");
            btn3.setBackgroundColor(Color.RED);
        });

//       Metodo 3
        btn3.setOnClickListener(this);

// Metodo 4
        btn4.setOnClickListener(new PressButton4());

        if (savedInstanceState != null){ // Se está resconstruyendo la actividad
            label.setText(savedInstanceState.getString("AVISO"));
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("AVISO",label.getText().toString());
    }

    @Override
    public void onClick(View view) {
        label.setText("Boton 3");

        for (int i =0 ; i < 1000000; i++){
            System.out.println("XXXX");
        }
        btn4.setBackgroundColor(Color.YELLOW);

    }

    class PressButton4 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            label.setText("Boton 4");
        }
    }

}
