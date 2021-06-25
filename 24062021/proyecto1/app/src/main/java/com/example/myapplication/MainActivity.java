package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Switch s1,s2;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        t1=findViewById(R.id.textView);
    }

    public void fondo(View v){
        if(s2.isChecked()) {
            findViewById(R.id.idFondo).setBackgroundColor(Color.rgb(100, 100, 100));
            s1.setTextColor(Color.rgb(250, 250, 250));
            s2.setTextColor(Color.rgb(250, 250, 250));
            t1.setTextColor(Color.rgb(250, 250, 250));
        }else{
            findViewById(R.id.idFondo).setBackgroundColor(Color.rgb(250, 250, 250));
            s1.setTextColor(Color.rgb(0, 0, 0));
            s2.setTextColor(Color.rgb(0, 0, 0));
            t1.setTextColor(Color.rgb(0, 0, 0));
        }
        Toast.makeText(this,"cambia color de fondo",Toast.LENGTH_LONG);
    }
    public void Letras_rojas (View v){
        if(s1.isChecked()) {
            t1.setTextColor(Color.rgb(250, 0, 0));
            s1.setTextColor(Color.rgb(250, 0, 0));
            s2.setTextColor(Color.rgb(250, 0, 0));
        }else{
            t1.setTextColor(Color.rgb(0, 0, 0));
            s1.setTextColor(Color.rgb(0, 0, 0));
            s2.setTextColor(Color.rgb(0, 0, 0));
        }
        Toast.makeText(this,"cambia color de letras",Toast.LENGTH_LONG);
    }
}