package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalcN extends AppCompatActivity {
    private EditText e1,e2;
    private TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_n);

        e1=findViewById(R.id.edit4);
        e2=findViewById(R.id.edit5);
        t1=findViewById(R.id.text4);
    }


    public void sumar(View v){
        Double valor1=Double.parseDouble(e1.getText().toString());
        Double valor2=Double.parseDouble(e2.getText().toString());
        Double suma=valor1+valor2;
        t1.setText("resultado:"+suma);
    }
    public void restar(View v){
        int valor1=Integer.parseInt(e1.getText().toString());
        int valor2=Integer.parseInt(e2.getText().toString());
        int resta=valor1-valor2;
        t1.setText("resultado:"+resta);
    }
    public void multi(View v){
        int valor1=Integer.parseInt(e1.getText().toString());
        int valor2=Integer.parseInt(e2.getText().toString());
        int mult=valor1*valor2;
        t1.setText("resultado:"+mult);
    }
    public void dividir(View v){
        Double valor1=Double.parseDouble(e1.getText().toString());
        Double valor2=Double.parseDouble(e2.getText().toString());
        Double divide=valor1/valor2;
        t1.setText("resultado:"+divide);
    }
    public void volver(View v){
        Intent inicio = new Intent(this, MainActivity.class );
        startActivity(inicio);
        finish();
    }
}