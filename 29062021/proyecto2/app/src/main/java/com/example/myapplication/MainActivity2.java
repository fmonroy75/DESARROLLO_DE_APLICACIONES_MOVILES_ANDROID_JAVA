package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        String valor=getIntent().getStringExtra("nombre");
        String valorp=getIntent().getStringExtra("prom");
        String arrprom[]=valorp.split(":");
        tv1.setText("hola "+valor);
        if (Double.parseDouble(arrprom[1])>40){
            tv2.setText("ha aprobado");
            tv2.setTextColor(Color.rgb(0,240,0));
        }else{
            tv2.setText("ha reprobado");
            tv2.setTextColor(Color.rgb(240,0,0));
        }
    }
    public void volver(View v){
         Intent i =new Intent(this, MainActivity.class);
         startActivity(i);
    }
}