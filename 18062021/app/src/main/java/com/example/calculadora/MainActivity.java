package com.example.calculadora;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calculadora.R;

public class MainActivity extends AppCompatActivity {
    private EditText e1,e2;
    private TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.edit1);
        e2=findViewById(R.id.edit2);
        t1=findViewById(R.id.Text);
    }

    public void sumar(View v){
        int valor1=Integer.parseInt(e1.getText().toString());
        int valor2=Integer.parseInt(e2.getText().toString());
        int suma=valor1+valor2;
        t1.setText("resultado:"+suma);
    }
}