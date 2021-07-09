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

    String KEY_LBL = "LABEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Aqui se creo la pantalla a partir del XML


        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        label = findViewById(R.id.lbl_Hello);
        //Method 1  - Innerclass
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        label.setText("Button 1 Pressed");
                        Intent intent = new Intent(MainActivity.this,AsyncTaskActivity.class);
                        startActivity(intent);
                    }
                }
        );
        //Method 2 - InnerClass lamda
        btn2.setOnClickListener((View  v) -> {
            label.setText("Button 2 Pressed");
            btn3.setBackgroundColor(Color.RED);
        });

        // Method 3  - Activity is a listener
        btn3.setOnClickListener(this);

        // Method 4 - Inner Class declared
        btn4.setOnClickListener(new PressButton4());

        if (savedInstanceState != null){ // Se está resconstruyendo la actividad
            label.setText(savedInstanceState.getString(KEY_LBL));
        }

    }

    @Override
    public void onClick(View view) {
        label.setText("Button 3 Pressed" );
        btn4.setBackgroundColor(Color.YELLOW);
    }

    class PressButton4 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            label.setText("Button 4 Pressed");
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_LBL,label.getText().toString());
    }
}
